package com.swp391.koibe.repositories;

import com.swp391.koibe.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidHistoryRepository extends JpaRepository<Bid,  Long> {
    List<Bid> findAllByAuctionKoiId(Long auctionKoiId);
    boolean existsByAuctionKoiIdAndBidderId(Long auctionKoiId, Long bidderId);
}
