package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UpdateAuctionDTO(
    @JsonProperty("title")
    String title,

    @JsonProperty("start_time")
    String startTime,

    @JsonProperty("end_time")
    String endTime,

    @JsonProperty("status")
    String statusName,

    @JsonProperty("auctioneer_id")
    long auctioneerId
) {}
