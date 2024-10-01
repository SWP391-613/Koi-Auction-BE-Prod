package com.swp391.koibe.services.biddinghistory;

import com.swp391.koibe.dtos.BidDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Bid;
import com.swp391.koibe.responses.BidResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBiddingHistoryService {
    Bid createBidHistory(Bid bid) throws DataNotFoundException;
    Bid getBidHistoryById(long id);
    Page<BidResponse> getAllBidHistories(Pageable pageable);
    Bid updateBidHistory(long id, Bid bid);
    void deleteBidHistory(long id);
    boolean existsByName(String name);
    void createBidHistories(List<Bid> bidHistories) throws DataNotFoundException;
    @Transactional
    BidResponse placeBid(BidDTO bidDTO) throws Exception;
    List<Bid> getBidsByAuctionKoiId(Long auctionKoiId);
    boolean hasBid(Long auctionKoiId, Long bidderId);
}
