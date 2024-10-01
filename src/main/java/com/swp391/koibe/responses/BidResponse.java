package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BidResponse {

    @JsonProperty("auction_koi_id")
    private Long auctionKoiId;

    @JsonProperty("bidder_id")
    private Long bidderId;

    @JsonProperty("bid_amount")
    private long bidAmount;

    @JsonProperty("bid_time")
    private String bidTime;

    @JsonProperty("bidder_name")
    private String bidderName;
}