package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionKoiResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("auction_id")
    private long auctionId;

    @JsonProperty("koi_id")
    private long koiId;

    @JsonProperty("base_price")
    private Float basePrice;

    @JsonProperty("current_bid")
    private int currentBid;

    @JsonProperty("current_bidder_id")
    private Long currentBidderId;

    @JsonProperty("is_sold")
    private boolean isSold;

    @JsonProperty("bid_method")
    private String bidMethod;

}
