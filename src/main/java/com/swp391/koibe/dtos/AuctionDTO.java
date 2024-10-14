package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record AuctionDTO (
    @JsonProperty("title")
    @NotBlank(message = "Title is required")
    String title,

    @JsonProperty("start_time")
    @NotBlank(message = "Start time is required")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{6}$",
        message = "Start time must be in the format yyyy-MM-dd HH:mm:ss.SSSSSS"
    )
    String startTime,

    @JsonProperty("end_time")
    @NotBlank(message = "End time is required")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{6}$",
        message = "End time must be in the format yyyy-MM-dd HH:mm:ss.SSSSSS"
    )
    String endTime,

    @JsonProperty("status")
    @NotBlank(message = "Auction status name is required")
    @Pattern(
        regexp = "^(ENDED|ONGOING|UPCOMING)$",
        message = "Auction status name must be ENDED, ONGOING or UPCOMING"
    )
    String statusName,

    @JsonProperty("auctioneer_id")
    @NotNull(message = "Auctioneer is required for one Auction")
    @Positive(message = "Auctioneer ID must be a positive number")
    long auctioneerId
) {}
