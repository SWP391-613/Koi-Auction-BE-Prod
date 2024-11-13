package com.swp391.koibe.services.redis.koi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swp391.koibe.responses.KoiInAuctionResponse;
import com.swp391.koibe.responses.KoiResponse;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface IKoiRedisService {
    //Clear cached data in Redis
    void clear();

    //@GetMapping("/get-kois-by-keyword")
    List<KoiInAuctionResponse> findKoiInAuctionByKeyword(
        String keyword,
        PageRequest pageRequest
    ) throws JsonProcessingException;

    void saveAllKoiFindInAuctionByKeyword(
        List<KoiInAuctionResponse> productResponses,
        String keyword,
        PageRequest pageRequest
    ) throws JsonProcessingException;

    //@GetMapping("/get-kois-owner-by-keyword-not-auth")
    List<KoiResponse> findKoiByKeyword(
        String keyword,
        Long breederId,
        PageRequest pageRequest
    ) throws JsonProcessingException;

    void saveAllKois(
        List<KoiResponse> productResponses,
        String keyword,
        Long breederId,
        PageRequest pageRequest
    ) throws JsonProcessingException;

}
