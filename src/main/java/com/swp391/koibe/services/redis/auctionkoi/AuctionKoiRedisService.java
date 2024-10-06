package com.swp391.koibe.services.redis.auctionkoi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.koibe.responses.AuctionKoiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionKoiRedisService implements IAuctionKoiRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;
    private String getKeyFrom(String keyword,
                       Integer bidMethod,
                       PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        String sortDirection = sort.getOrderFor("id")
                .getDirection() == Sort.Direction.ASC ? "asc": "desc";
        String key = String.format("all_auctionkois:%s:%d:%d:%d:%s",
                keyword, bidMethod, pageNumber, pageSize, sortDirection);
        return key;
    }
    @Override
    public List<AuctionKoiResponse> getAllAuctionKois(String keyword,
                                                Integer bidMethod,
                                                PageRequest pageRequest) throws JsonProcessingException {

        if(useRedisCache == false) {
            return null;
        }
        String key = this.getKeyFrom(keyword, bidMethod, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<AuctionKoiResponse> auctionResponses =
                json != null ?
                redisObjectMapper.readValue(json, new TypeReference<List<AuctionKoiResponse>>() {})
                : null;
        return auctionResponses;
    }
    @Override
    public void clear(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    //save to Redis
    public void saveAllAuctionKois(List<AuctionKoiResponse> auctionKoiResponses,
                                String keyword,
                                Integer bidMethod,
                                PageRequest pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, bidMethod, pageRequest);
        String json = redisObjectMapper.writeValueAsString(auctionKoiResponses);
        redisTemplate.opsForValue().set(key, json);
    }
}
