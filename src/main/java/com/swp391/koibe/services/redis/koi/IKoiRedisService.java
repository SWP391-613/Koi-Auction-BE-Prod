package com.swp391.koibe.services.redis.koi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swp391.koibe.responses.KoiResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface IKoiRedisService {
    //Clear cached data in Redis
    void clear();
    List<KoiResponse> getAllKois(
            String keyword,
            Long categoryId, PageRequest pageRequest) throws JsonProcessingException;
    void saveAllKois(List<KoiResponse> productResponses,
                                String keyword,
                                Long categoryId,
                                PageRequest pageRequest) throws JsonProcessingException;

}
