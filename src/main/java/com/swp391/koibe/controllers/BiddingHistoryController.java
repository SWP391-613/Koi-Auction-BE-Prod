package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.exceptions.BiddingRuleException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.*;
import com.swp391.koibe.repositories.AuctionParticipantRepository;
import com.swp391.koibe.responses.BidResponse;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.responses.pagination.BiddingHistoryPaginationResponse;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/bidding")
@RequiredArgsConstructor
public class BiddingHistoryController {

    private final IBiddingHistoryService biddingHistoryService;
    private final SimpMessagingTemplate messagingTemplate;
    private final IAuctionKoiService auctionKoiService;

    @GetMapping("/{id}")
    public ResponseEntity<List<BidResponse>> getBiddingHistoryByAuctionKoiId(@PathVariable long id) {
        List<Bid> bidHistories = biddingHistoryService.getBidsByAuctionKoiId(id);
        List<BidResponse> bidResponses = bidHistories.stream()
                .map(DTOConverter::convertToBidDTO) // Convert Bid to BidResponse
                .collect(Collectors.toList());
        // by bid time newest to oldest
        Comparator<BidResponse> byBidTime = Comparator.comparing(BidResponse::getBidTime).reversed();
        bidResponses.sort(byBidTime);
        return ResponseEntity.ok(bidResponses);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBiddingHistory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {

        BiddingHistoryPaginationResponse response = new BiddingHistoryPaginationResponse();

        try {

            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<BidResponse> bids = biddingHistoryService.getAllBidHistories(pageRequest);
            response.setItems(bids.getContent());
            response.setTotalPage(bids.getTotalPages());
            response.setTotalItem(bids.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/bid/{auctionKoiId}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @MessageMapping("/auctionkoi/{auctionKoiId}/bid")
    public ResponseEntity<?> processBid(
            @DestinationVariable @PathVariable Long auctionKoiId,
            @RequestBody BidDTO bidDTO) throws Exception {
        try {
            BidResponse bidResponse = biddingHistoryService.placeBid(bidDTO);

            // Check if the auction koi is sold
            AuctionKoi auctionKoi = auctionKoiService.getAuctionKoiById(auctionKoiId);
            boolean isSold = auctionKoi.isSold();

            messagingTemplate.convertAndSend("/topic/auctionkoi/" + auctionKoiId, bidResponse);

            // Return a response that includes both the bid response and the isSold status
            Map<String, Object> response = new HashMap<>();
            response.put("bidResponse", bidResponse);
            response.put("isSold", isSold);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BaseResponse<Object> response = new BaseResponse<>();
            response.setReason(BiddingRuleException.class.getSimpleName());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{auctionKoiId}/{userId}")
    public ResponseEntity<?> getUserHighestBid(
            @PathVariable Long auctionKoiId,
            @PathVariable Long userId) throws Exception {
        try {
            BidResponse bidResponse = biddingHistoryService.getBidderHighestBid(auctionKoiId, userId);
            return ResponseEntity.ok(bidResponse);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setReason(BiddingRuleException.class.getSimpleName());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
