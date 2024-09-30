package com.swp391.koibe.services.biddinghistory;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.BidHistoryRepository;
import com.swp391.koibe.responses.BidResponse;
import com.swp391.koibe.services.auctionkoi.IAuctionKoiService;
import com.swp391.koibe.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BiddingHistoryService implements IBiddingHistoryService {

    private final BidHistoryRepository bidHistoryRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private IAuctionKoiService auctionKoiService;

    @Autowired
    private IUserService userService;

    @Override
    public Bid createBidHistory(Bid bid) throws DataNotFoundException {
        return bidHistoryRepository.save(bid);
    }

    @Override
    public Bid getBidHistoryById(long id) {
        return null;
    }

    @Override
    public Page<Bid> getAllBidHistories(Pageable pageable) {
        return null;
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
        User bidder = userService.getUserById(bidRequest.bidderId());

        if (auctionKoi.getCurrentBid() >= bidRequest.bidAmount()) {
            throw new IllegalArgumentException("Bid amount must be higher than the current bid");
        }

        Bid bid = Bid.builder()
                .auctionKoi(auctionKoi)
                .bidder(bidder)
                .bidAmount(bidRequest.bidAmount())
                .bidTime(LocalDateTime.now())
                .build();

        bidHistoryRepository.save(bid);

        auctionKoi.setCurrentBid(bidRequest.bidAmount());
        auctionKoi.setCurrentBidderId(bidder.getId());
        auctionKoiService.updateAuctionKoi(auctionKoi.getId(), auctionKoi);

        BidResponse bidResponse = BidResponse.builder()
                .auctionKoiId(auctionKoi.getId())
                .bidderId(bidder.getId())
                .bidAmount(bidRequest.bidAmount())
                .bidderName(bidder.getFirstName() + " " + bidder.getLastName())
                .bidTime(bid.getBidTime())
                .build();

        messagingTemplate.convertAndSend("/topic/auction/" + auctionKoi.getId(), bidResponse);

        return bidResponse;
    }

    @Override
    public List<Bid> getBidsByAuctionKoiId(Long auctionKoiId) {
        return bidHistoryRepository.findAllByAuctionKoiId(auctionKoiId);
    }

    @Override
    //(biddingHistoryService.hasBid(auction_koi_id, bid.getBidder().getId()))
    public boolean hasBid(Long auctionKoiId, Long bidderId) {
        return bidHistoryRepository.existsByAuctionKoiIdAndBidderId(auctionKoiId, bidderId);
    }
}
