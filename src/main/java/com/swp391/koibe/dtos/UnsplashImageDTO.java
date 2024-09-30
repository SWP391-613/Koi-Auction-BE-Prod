package com.swp391.koibe.dtos;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Builder
public record UnsplashImageDTO (
    String slug,
    String description,
    String alt_description,
    Map<String, String> urls
){}
