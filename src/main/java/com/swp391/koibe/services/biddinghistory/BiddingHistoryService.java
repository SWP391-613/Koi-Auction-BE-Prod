package com.swp391.koibe.services.biddinghistory;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.dtos.UpdateUserDTO;
import com.swp391.koibe.dtos.auctionkoi.UpdateAuctionKoiDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.BidHistoryRepository;
import com.swp391.koibe.responses.BidResponse;
import com.swp391.koibe.services.Biddable;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.token.ITokenService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BiddingHistoryService implements IBiddingHistoryService, Biddable {

    private final BidHistoryRepository bidHistoryRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final IAuctionKoiService auctionKoiService;

    private final IUserService userService;

    private final ITokenService tokenService;


    @Override
    public Bid createBidHistory(Bid bid) throws DataNotFoundException {
        return bidHistoryRepository.save(bid);
    }

    @Override
    public Bid getBidHistoryById(long id) {
        return null;
    }

    @Override
    public Page<BidResponse> getAllBidHistories(Pageable pageable) {
        return bidHistoryRepository.findAll(pageable).map(DTOConverter::convertToBidDTO);
    }

    @Override
    public Bid updateBidHistory(long id, Bid bid) {
        return null;
    }

    @Override
    public void deleteBidHistory(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public void createBidHistories(List<Bid> bidHistories) throws DataNotFoundException {
        for (Bid bid : bidHistories) {
            createBidHistory(bid);
        }
    }

    @Transactional
    @Override
    public BidResponse placeBid(BidDTO bidRequest) throws Exception {
        AuctionKoi auctionKoi = auctionKoiService.getAuctionKoiById(bidRequest.auctionKoiId());
        User bidder = tokenService.findUserByToken(bidRequest.bidderToken()).getUser();
        Auction auction = auctionKoi.getAuction();

        Bid bid = Bid.builder()
                .auctionKoi(auctionKoi)
                .bidder(bidder)
                .bidAmount(bidRequest.bidAmount())
                .bidTime(LocalDateTime.now())
                .build();

        if (!DateTimeUtils.isAuctionActive(auction.getStartTime(), auction.getEndTime(), bid.getBidTime())) {
            throw new IllegalArgumentException("AuctionKoi has been ended!");
        }

        // check if koi in auction is sold or not
        if (auctionKoi.isSold()) {
            throw new IllegalArgumentException("AuctionKoi has been sold!");
        }

        // because the current bid can be null, so we need to check if the current bid is null
        if (auctionKoi.getCurrentBid() == null) {
            auctionKoi.setCurrentBid(0L);
        }

        // Check if the bid amount is higher than the current bid
        if (auctionKoi.getCurrentBid() >= bid.getBidAmount()) {
            throw new IllegalArgumentException("Bid amount must be higher than the current bid");
        }

        // Check if the bid amount is a multiple of the bid step
        if ((bid.getBidAmount() - auctionKoi.getCurrentBid() == 0 ? auctionKoi.getBasePrice()
                : auctionKoi.getCurrentBid()) % auctionKoi.getBidStep() != 0) {
            throw new IllegalArgumentException("Bid amount must be a multiple of the bid step");
        }

        // Check if the bidder is the owner of the koi
        if (bidder.getId() == auctionKoi.getKoi().getOwner().getId()) {
            throw new IllegalArgumentException("Owner can not Bid your Koi");
        }

        // any bid method logic will implement later

        // Update the current bid and current bidder of the auction koi
        UpdateAuctionKoiDTO updateAuctionKoiDTO = UpdateAuctionKoiDTO.builder()
                .basePrice(auctionKoi.getBasePrice())
                .bidStep(auctionKoi.getBidStep())
                .bidMethod(String.valueOf(auctionKoi.getBidMethod()))
                .currentBid(bid.getBidAmount())
                .currentBidderId(bid.getBidder().getId())
                .isSold(auctionKoi.isSold())
                .build();

        if (getBidderLatestBid(auctionKoi.getId(), bidder.getId()) == null){
            // save the user to auction participant
        }

        // calculate bidder bid amount
        Long bidedAmount = bid.getBidAmount() - (getBidderLatestBid(auctionKoi.getId(), bidder.getId()) != null
                ? getBidderLatestBid(auctionKoi.getId(), bidder.getId()).getBidAmount()
                : 0);



        bidHistoryRepository.save(bid);
//        userService.updateUserBalance(bidder.getId(), bidedAmount);
        auctionKoiService.updateAuctionKoi(auctionKoi.getId(), updateAuctionKoiDTO);

        BidResponse bidResponse = BidResponse.builder()
                .auctionKoiId(auctionKoi.getId())
                .bidderId(bidder.getId())
                .bidAmount(bidRequest.bidAmount())
                .bidderName(bidder.getFirstName() + " " + bidder.getLastName())
                .bidTime(bid.getBidTime().toString())
                .build();

        // Send the update to the specific auction koi topic
        messagingTemplate.convertAndSend("/topic/auctionkoi/" + auctionKoi.getId(), bidResponse);

        return bidResponse;
    }

    @Override
    public List<Bid> getBidsByAuctionKoiId(Long auctionKoiId) {
        return bidHistoryRepository.findAllByAuctionKoiId(auctionKoiId);
    }

    @Override
    public boolean hasBid(Long auctionKoiId, Long bidderId) {
        return bidHistoryRepository.existsByAuctionKoiIdAndBidderId(auctionKoiId, bidderId);
    }

    @Override
    public Bid getBidderLatestBid(Long auctionKoiId, Long bidderId) {
        List<Bid> bidList = bidHistoryRepository.findAllByAuctionKoiId(auctionKoiId);
        if (bidList.isEmpty()) {
            return null;
        }
        bidList = bidList.stream()
                .filter(bid -> bid.getBidder().getId().equals(bidderId))
                .sorted(Comparator.comparing(Bid::getBidTime).reversed())
                .toList();
        return bidList.isEmpty() ? null : bidList.get(0);
    }

    @Override
    public void ascending() {
        //for the bid method ascending
        //user can bid until the end time

    }

    @Override
    public void descending() {
        //for the bid method descending
        //the koi in auctionkoi of this method will drop price to the lowest
        //user who bid first is the winner and the AuctionKoi's is_sold is true


    }

    @Override
    public void fixedPrice() {
        //for the bid method fixedPrice
        //like the e-commerce, sold the Koi in fixed price
        //user who buy first is the winner the AuctionKoi's is_sold is true

    }

    @Override
    public void sealed() {
        //for the bid method sealed
        //user can only bid one time and bid price will be hidden from other bidder
        //at the auction end time, the bid list will open and the highest bid is winner

    }
}
