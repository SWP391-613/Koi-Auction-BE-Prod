package com.swp391.koibe.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BidResponse {
    private Long auctionKoiId;
    private Long bidderId;
    private int bidAmount;
    private LocalDateTime bidTime;
    private String bidderName;
}