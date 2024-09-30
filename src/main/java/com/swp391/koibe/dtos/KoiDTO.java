package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.enums.EKoiStatus;
import lombok.Builder;

@Builder
public record KoiDTO(
    @JsonProperty("name") String name,
    @JsonProperty("price") Float price,
    @JsonProperty("tracking_status") EKoiStatus EKoiStatus,
    @JsonProperty("is_display") int isDisplay,
    @JsonProperty("thumbnail") String thumbnail,
    @JsonProperty("sex") String sex,
    @JsonProperty("length") int length,
    @JsonProperty("age") int age,
    @JsonProperty("description") String description,
    @JsonProperty("category_id") int categoryId,
    @JsonProperty("user_id") Long ownerId
) {}
