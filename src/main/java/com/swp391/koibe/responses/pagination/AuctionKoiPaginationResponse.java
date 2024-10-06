package com.swp391.koibe.responses.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.responses.AuctionKoiResponse;
import com.swp391.koibe.responses.base.BasePaginationResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionKoiPaginationResponse extends BasePaginationResponse {

    @JsonProperty("item")
    private List<AuctionKoiResponse> items; // List of auction koi
}
