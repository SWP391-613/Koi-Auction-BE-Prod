package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.*;
import com.swp391.koibe.repositories.AuctionParticipantRepository;
import com.swp391.koibe.responses.BidResponse;
import com.swp391.koibe.responses.pagination.BiddingHistoryPaginationResponse;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.biddinghistory.IBiddingHistoryService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.DateTimeUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/auctionkoidetails")
@RequiredArgsConstructor
public class BiddingHistoryController {

    private final IAuctionKoiService auctionKoiService;
    private final IUserService userService;
    private final IBiddingHistoryService biddingHistoryService;
    private final AuctionParticipantRepository auctionParticipantRepository;

    @PostMapping("/generateFakeAuctionKoiDetails")
    public ResponseEntity<String> generateFakeAuctionKoiDetails() {
        try {
            List<AuctionKoi> auctionKois = auctionKoiService.getAuctionIsSold();
            List<User> users = userService.getAllUsers();
            Random random = new Random();

            // Group AuctionKois by Auction
            Map<Auction, List<AuctionKoi>> auctionKoisByAuction = auctionKois.stream()
                    .collect(Collectors.groupingBy(AuctionKoi::getAuction));

            for (Map.Entry<Auction, List<AuctionKoi>> entry : auctionKoisByAuction.entrySet()) {
                Auction auction = entry.getKey();
                List<AuctionKoi> auctionKoisForAuction = entry.getValue();
                Set<Long> participantIds = new HashSet<>();

                for (AuctionKoi auctionKoi : auctionKoisForAuction) {
                    List<Bid> bidHistories = new ArrayList<>();
                    int numberOfBids = random.nextInt(5, 16); // Random number of bids between 5 and 15
                    int currentBidAmount = auctionKoi.getBasePrice().intValue();

                    for (int i = 1; i <= numberOfBids; i++) {
                        long ownerId = auctionKoi.getKoi().getOwner().getId();
                        User bidder = users.stream()
                                .filter(user -> user.getId() != ownerId)
                                .skip(random.nextInt(users.size() - 1))
                                .findFirst()
                                .orElseThrow(() -> new DataNotFoundException("No suitable bidder found"));

                        Bid bid = new Bid();
                        bid.setAuctionKoi(auctionKoi);
                        bid.setBidder(bidder);

                        // Ensure each bid is higher than the previous one
                        currentBidAmount += auctionKoi.getBidStep();
                        bid.setBidAmount(Math.round(currentBidAmount));

                        LocalDateTime bidTime = auctionKoi.getAuction().getStartTime().plusHours(i);
                        bid.setBidTime(bidTime);

                        bidHistories.add(bid);

                        // Add participant if not already added for this auction
                        if (!participantIds.contains(bidder.getId())) {
                            AuctionParticipant participant = new AuctionParticipant();
                            participant.setAuction(auction);
                            participant.setUser(bidder);
                            participant.setJoinTime(bid.getBidTime());
                            auctionParticipantRepository.save(participant);
                            participantIds.add(bidder.getId());
                        }
                    }

                    // Sort bid histories by time
                    bidHistories.sort(Comparator.comparing(Bid::getBidTime));

                    // Set the last bid as the winning bid
                    Bid winningBid = bidHistories.get(bidHistories.size() - 1);
                    auctionKoi.setCurrentBid(winningBid.getBidAmount());
                    auctionKoi.setCurrentBidderId(winningBid.getBidder().getId());

                    // Save all bid histories in batch
                    biddingHistoryService.createBidHistories(bidHistories);

                    UpdateAuctionKoiDTO updateAuctionKoiDTO = UpdateAuctionKoiDTO.builder()
                            .basePrice(auctionKoi.getBasePrice())
                            .bidStep(auctionKoi.getBidStep())
                            .bidMethod(String.valueOf(auctionKoi.getBidMethod()))
                            .currentBid(auctionKoi.getCurrentBid())
                            .currentBidderId(auctionKoi.getCurrentBidderId())
                            .isSold(auctionKoi.isSold())
                            .build();

                    // Update the AuctionKoi
                    auctionKoiService.updateAuctionKoi(auctionKoi.getAuction().getId(),
                                                       auctionKoi.getKoi().getId(),
                                                       updateAuctionKoiDTO);
                }
            }

            return ResponseEntity.ok("Fake auction koi details and participants generated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating fake auction koi details: " + e.getMessage());
        }
    }

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
        @RequestParam("limit") int limit
    ){

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

    @PostMapping("/auctionkois/{auction_koi_id}/auction/{auction_id}")
    //localhost:8080/api/v1/auctionkoidetails/auctionkois/1/auction/1
    public ResponseEntity<?> createBiddingHistory(
        @PathVariable long auction_koi_id,
        @PathVariable long auction_id,
        @Valid @RequestBody BidDTO bid,
        BindingResult result
    ) {

        if(result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        try {
            AuctionKoi auctionKoi = auctionKoiService.getAuctionKoiById(auction_koi_id);
            Auction auction = auctionKoi.getAuction();
            //check auction is close or not
            if(!DateTimeUtils.isAuctionActive(auction.getStartTime(), auction.getEndTime(), DateTimeUtils.parseTime(bid.bidTime()))){
                return ResponseEntity.badRequest().body("Auction is closed");
            }

            //check if koi in auction is sold or not
            if(auctionKoi.isSold()){
                return ResponseEntity.badRequest().body("Koi in auction is already sold");
            }

            if (auctionKoi.getAuction().getId() != auction_id) {
                return ResponseEntity.badRequest()
                    .body("Auction koi does not belong to the auction");
            }

            // Check if the bid amount is higher than the current bid
            if (bid.bidAmount() <= auctionKoi.getCurrentBid()) {
                return ResponseEntity.badRequest()
                    .body("Bid amount must be higher than the current bid");
            }

            // Check if the bid amount is a multiple of the bid step
            if ((bid.bidAmount() - auctionKoi.getCurrentBid()) % auctionKoi.getBidStep() != 0) {
                return ResponseEntity.badRequest()
                    .body("Bid amount must be a multiple of the bid step, current bid step is " + auctionKoi.getBidStep());
            }

            // Check if the auction is still ongoing
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(auctionKoi.getAuction().getStartTime()) || now.isAfter(
                auctionKoi.getAuction().getEndTime())) {
                return ResponseEntity.badRequest().body("Auction is not ongoing");
            }

            // Check if the bidder is the owner of the koi
            if (Objects.equals(bid.bidderId(), auctionKoi.getKoi().getOwner().getId())) {
                return ResponseEntity.badRequest().body("Owner of the koi cannot bid");
            }

            // Check if the bidder has already placed a bid
            if (biddingHistoryService.hasBid(auction_koi_id, bid.bidderId())) {
                return ResponseEntity.badRequest().body("Bidder has already placed a bid");
            }

            // Create the bidding history
            Bid newBid = Bid.builder()
                .bidAmount(bid.bidAmount())
                .bidTime(LocalDateTime.now())
                .bidder(userService.getUserById(bid.bidderId()))
                .auctionKoi(auctionKoi)
                .build();
            biddingHistoryService.createBidHistory(newBid);

            // Update the current bid and current bidder of the auction koi
            auctionKoi.setCurrentBid(bid.bidAmount());
            auctionKoi.setCurrentBidderId(bid.bidderId());

            UpdateAuctionKoiDTO udpateAuctionKoiDTO = UpdateAuctionKoiDTO.builder()
                .basePrice(auctionKoi.getBasePrice())
                .bidStep(auctionKoi.getBidStep())
                .bidMethod(String.valueOf(auctionKoi.getBidMethod()))
                .currentBid(bid.bidAmount())
                .currentBidderId(bid.bidderId())
                .isSold(auctionKoi.isSold())
                .build();

            auctionKoiService.updateAuctionKoi(auction_id, auctionKoi.getKoi().getId(), udpateAuctionKoiDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Bid created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
