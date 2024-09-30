package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.responses.BidResponse;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BidWebSocketController {

    private final IBiddingHistoryService biddingHistoryService;

    @MessageMapping("/bid")
    @SendTo("/topic/auction/{auctionKoiId}")
    public BidResponse processBid(@Payload BidDTO bidDTO) throws Exception {
        return biddingHistoryService.placeBid(bidDTO);
    }
}