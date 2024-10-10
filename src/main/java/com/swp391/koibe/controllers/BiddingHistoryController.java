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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/bidding")
@RequiredArgsConstructor
public class BiddingHistoryController {

    private final IAuctionKoiService auctionKoiService;
    private final IUserService userService;
    private final IBiddingHistoryService biddingHistoryService;
    private final AuctionParticipantRepository auctionParticipantRepository;
    private final SimpMessagingTemplate messagingTemplate;

//    @PostMapping("/generatefakebiddinghistory")
//    public ResponseEntity<String> generateFakeAuctionKoiDetails() {
//        try {
//            List<AuctionKoi> auctionKois = auctionKoiService.getAuctionIsSold();
//            List<User> users = userService.getAllUsers();
//            Random random = new Random();
//
//            // Group AuctionKois by Auction
//            Map<Auction, List<AuctionKoi>> auctionKoisByAuction = auctionKois.stream()
//                    .collect(Collectors.groupingBy(AuctionKoi::getAuction));
//
//            for (Map.Entry<Auction, List<AuctionKoi>> entry : auctionKoisByAuction.entrySet()) {
//                Auction auction = entry.getKey();
//                List<AuctionKoi> auctionKoisForAuction = entry.getValue();
//                Set<Long> participantIds = new HashSet<>();
//
//                for (AuctionKoi auctionKoi : auctionKoisForAuction) {
//                    List<Bid> bidHistories = new ArrayList<>();
//                    int numberOfBids = random.nextInt(5, 6); // Random number of bids between 5 and 15
//                    int currentBidAmount = auctionKoi.getBasePrice().intValue();
//
//                    for (int i = 1; i <= numberOfBids; i++) {
//                        long ownerId = auctionKoi.getKoi().getOwner().getId();
//                        User bidder = users.stream()
//                                .filter(user -> user.getId() != ownerId && user.getId() >= 1 && user.getId() <= 20)
//                                .skip(random.nextInt((int) users.stream().filter(user -> user.getId() != ownerId && user.getId() >= 1 && user.getId() <= 20).count()))
//                                .findFirst()
//                                .orElseThrow(() -> new DataNotFoundException("No suitable bidder found"));
//
//                        Bid bid = new Bid();
//                        bid.setAuctionKoi(auctionKoi);
//                        bid.setBidder(bidder);
//
//                        // Ensure each bid is higher than the previous one
//                        currentBidAmount += auctionKoi.getBidStep();
//                        bid.setBidAmount(Math.round(currentBidAmount));
//
//                        LocalDateTime bidTime = auctionKoi.getAuction().getStartTime().plusHours(i);
//                        bid.setBidTime(bidTime);
//
//                        bidHistories.add(bid);
//
//                        // Add participant if not already added for this auction
//                        if (!participantIds.contains(bidder.getId())) {
//                            AuctionParticipant participant = new AuctionParticipant();
//                            participant.setAuction(auction);
//                            participant.setUser(bidder);
//                            participant.setJoinTime(bid.getBidTime());
//                            auctionParticipantRepository.save(participant);
//                            participantIds.add(bidder.getId());
//                        }
//                    }
//
//                    // Sort bid histories by time
//                    bidHistories.sort(Comparator.comparing(Bid::getBidTime));
//
//                    // Set the last bid as the winning bid
//                    Bid winningBid = bidHistories.get(bidHistories.size() - 1);
//                    auctionKoi.setCurrentBid(winningBid.getBidAmount());
//                    auctionKoi.setCurrentBidderId(winningBid.getBidder().getId());
//
//                    // Save all bid histories in batch
//                    biddingHistoryService.createBidHistories(bidHistories);
//
//                    UpdateAuctionKoiDTO updateAuctionKoiDTO = UpdateAuctionKoiDTO.builder()
//                            .basePrice(auctionKoi.getBasePrice())
//                            .bidStep(auctionKoi.getBidStep())
//                            .bidMethod(String.valueOf(auctionKoi.getBidMethod()))
//                            .currentBid(auctionKoi.getCurrentBid())
//                            .currentBidderId(auctionKoi.getCurrentBidderId())
//                            .isSold(auctionKoi.isSold())
//                            .build();
//
//                    // Update the AuctionKoi
//                    auctionKoiService.updateAuctionKoi(auctionKoi.getAuction().getId(),
//                            updateAuctionKoiDTO);
//                }
//            }
//
//            return ResponseEntity.ok("Fake auction koi details and participants generated");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error generating fake auction koi details: " + e.getMessage());
//        }
//    }

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
    @MessageMapping("/auctionkoi/{auctionKoiId}/bid")
//    @SendTo("/topic/auctionkoi/{auctionKoiId}")
    public ResponseEntity<?> processBid(@DestinationVariable @PathVariable Long auctionKoiId, @RequestBody BidDTO bidDTO) throws Exception {
        try {
            BidResponse bidResponse = biddingHistoryService.placeBid(bidDTO);
            messagingTemplate.convertAndSend("/topic/auctionkoi/" + auctionKoiId, bidResponse);

            return ResponseEntity.ok(bidResponse);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setReason(BiddingRuleException.class.getSimpleName());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        try {
            return ResponseEntity.ok().body(biddingHistoryService.getBidderLatestBid(1L, 5L));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // You can add more methods for other types of updates if needed
}
