package com.swp391.koibe.controllers;

import com.swp391.koibe.configs.UnsplashConfig;
import com.swp391.koibe.dtos.UnsplashImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@Deprecated
public class ProductController {

    private final RestTemplate restTemplate;
    private final UnsplashConfig unsplashConfig;

    @GetMapping("/test/view")
    public ResponseEntity<?> getProducts(@RequestParam String query, @RequestParam int perPage) {
        // Use values from UnsplashConfig
        String url = String.format("%s/search/photos?query=%s&client_id=%s&per_page=%d",
                                   unsplashConfig.getRoot(), query, unsplashConfig.getClientId(), perPage);

        // Call Unsplash API
        ResponseEntity<Map> unsplashResponse = restTemplate.getForEntity(url, Map.class);

        // Return the processed result as the response
        return ResponseEntity.ok(getPhotos(unsplashResponse));
    }

    private List<UnsplashImageDTO> getPhotos(ResponseEntity<Map> unsplashResponse) {
        // Extract the results array
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results = (List<Map<String, Object>>) ((Map<String, Object>) unsplashResponse.getBody()).get("results");

        // Convert results to UnsplashImageDTO objects using Builder pattern
        return results.stream()
            .map(result -> UnsplashImageDTO.builder()
                .slug((String) result.get("slug"))
                .description((String) result.get("description"))
                .alt_description((String) result.get("alt_description"))
                .urls((Map<String, String>) result.get("urls"))
                .build())
            .collect(Collectors.toList());
    }
}
