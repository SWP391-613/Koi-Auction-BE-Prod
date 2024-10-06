package com.swp391.koibe.services.redis.bid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swp391.koibe.responses.BidResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface IBidRedisService {
    //Clear cached data in Redis
    void clear();
    List<BidResponse> getAllBids(
            String keyword,
            Long categoryId, PageRequest pageRequest) throws JsonProcessingException;
    void saveAllBids(List<BidResponse> productResponses,
                                String keyword,
                                Long categoryId,
                                PageRequest pageRequest) throws JsonProcessingException;

}
