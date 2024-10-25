package com.swp391.koibe.services.redis.koi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swp391.koibe.dtos.KoiCacheData;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.pagination.KoiPaginationResponse;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IKoiRedisService {
    //Clear cached data in Redis
    void clear();

    List<KoiResponse> getAllKois(
        String keyword,
        Long categoryId,
        PageRequest pageRequest
    ) throws JsonProcessingException;

    List<KoiResponse> findKoiByKeyword(
        String keyword,
        Long breederId,
        PageRequest pageRequest
    ) throws JsonProcessingException;

    //GET: get-kois-owner-by-keyword-not-auth
    Map<String, Object> findKoiPaginationByKeyword(
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

    void saveKoiPaginationResponse(List<KoiResponse> koiResponses,
                                        String keyword,
                                        Long breederId,
                                        PageRequest pageRequest,
                                        long totalElements,  // Add these parameters
                                        int totalPages) throws JsonProcessingException;

}
