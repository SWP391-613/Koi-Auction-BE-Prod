package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartItemDTO(
    @JsonProperty("koi_id")
    Long koiId,
    @JsonProperty("quantity")
    Integer quantity
) {}
