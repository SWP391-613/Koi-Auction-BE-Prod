package com.swp391.koibe.services.redis.auction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swp391.koibe.responses.AuctionResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface IAuctionRedisService {
    //Clear cached data in Redis
    void clear();
    List<AuctionResponse> getAllAuctions(
            String keyword,
            Long categoryId, PageRequest pageRequest) throws JsonProcessingException;
    void saveAllAuctions(List<AuctionResponse> productResponses,
                                String keyword,
                                Long categoryId,
                                PageRequest pageRequest) throws JsonProcessingException;

}
