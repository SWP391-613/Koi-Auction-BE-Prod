package com.swp391.koibe.services.redis.koi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.koibe.dtos.KoiCacheData;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.pagination.KoiPaginationResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KoiRedisService implements IKoiRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;

    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    private String getKeyFrom(
        String keyword,
        long data,
        PageRequest pageRequest
    ) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        String sortDirection = Objects.requireNonNull(sort.getOrderFor("id"))
            .getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        String key = String.format("all_breeder_kois:%s:%d:%d:%d:%s",
                                   keyword, data, pageNumber, pageSize, sortDirection);
        return key;
    }

    @Override
    public List<KoiResponse> getAllKois(String keyword,
                                        Long categoryId,
                                        PageRequest pageRequest) throws JsonProcessingException {

        if (!useRedisCache) {
            return null;
        }
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<KoiResponse> auctionResponses =
            json != null ?
                redisObjectMapper.readValue(json, new TypeReference<List<KoiResponse>>() {
                })
                : null;
        return auctionResponses;
    }

    @Override
    public List<KoiResponse> findKoiByKeyword(
        String keyword,
        Long breederId,
        PageRequest pageRequest
    ) throws JsonProcessingException {

        if (!useRedisCache) {
            return null;
        }

        String key = this.getKeyFrom(keyword, breederId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ?
            redisObjectMapper.readValue(json, new TypeReference<>() {
            })
            : null;
    }



    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection()
            .serverCommands();
    }

    @Override
    //save to Redis
    public void saveAllKois(List<KoiResponse> koiResponses,
                            String keyword,
                            Long breederId,
                            PageRequest pageRequest
    ) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, breederId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(koiResponses);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public Map<String, Object> findKoiPaginationByKeyword(String keyword,
                                                            Long breederId,
                                                            PageRequest pageRequest) throws JsonProcessingException {
        if (!useRedisCache) {
            return null;
        }

        String key = this.getKeyFrom(keyword, breederId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ?
            redisObjectMapper.readValue(json, new TypeReference<Map<String, Object>>() {})
            : null;
    }

    @Override
    public void saveKoiPaginationResponse(List<KoiResponse> koiResponses,
                                          String keyword,
                                          Long breederId,
                                          PageRequest pageRequest,
                                          long totalElement,  // Add these parameters
                                          int totalPage) throws JsonProcessingException {
        Map<String, Object> cacheData = new HashMap<>();
        cacheData.put("item", koiResponses);
        cacheData.put("totalElement", totalElement);
        cacheData.put("totalPage", totalPage);

        String key = this.getKeyFrom(keyword, breederId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(cacheData);
        redisTemplate.opsForValue().set(key, json);
    }
}
