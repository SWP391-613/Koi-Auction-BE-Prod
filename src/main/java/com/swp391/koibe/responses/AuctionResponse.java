package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.responses.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionResponse extends BaseResponse<Auction> {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("auctioneer_id")
    private long auctioneerId;
}
