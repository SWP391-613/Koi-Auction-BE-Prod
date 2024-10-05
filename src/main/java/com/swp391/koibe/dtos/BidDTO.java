package com.swp391.koibe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BidDTO (
    @JsonProperty("bid_amount")
    @Min(value = 1, message = "Bid amount must be greater than 0")
    @NotNull(message = "Bid amount is required")
    long bidAmount,

//    @JsonProperty("bid_time")
//    @NotNull(message = "Bid time is required")
//    String bidTime,

    @JsonProperty("auction_koi_id")
    @NotNull(message = "Auction koi ID is required")
    Long auctionKoiId,

    @NotNull(message = "Bidder ID is required")
    @JsonProperty("bidder_token") String bidderToken
) {}

//NotBlank only on String, either string is empty or null
//Not null on any type, either the value is null or not