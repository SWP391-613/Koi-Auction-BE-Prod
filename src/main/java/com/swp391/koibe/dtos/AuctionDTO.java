package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuctionDTO (
    @JsonProperty("title")
    @NotBlank(message = "Title is required")
    String title,

    @JsonProperty("start_time")
    @NotBlank(message = "Start time is required")
    String startTime,

    @JsonProperty("end_time")
    @NotBlank(message = "End time is required")
    String endTime,

    @JsonProperty("status")
    @NotBlank(message = "Status name is required")
    String statusName
) {}
