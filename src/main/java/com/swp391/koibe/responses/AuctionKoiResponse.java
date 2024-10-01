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

    @JsonProperty("base_price")
    private long basePrice;

    @JsonProperty("bid_step")
    private long bidStep;

    @JsonProperty("bid_method")
    private String bidMethod;

    @JsonProperty("current_bid")
    private long currentBid;

    @JsonProperty("current_bidder_id")
    private Long currentBidderId;

    @JsonProperty("is_sold")
    private boolean isSold;

    @JsonProperty("auction_id")
    private long auctionId;

    @JsonProperty("koi_id")
    private long koiId;

}
