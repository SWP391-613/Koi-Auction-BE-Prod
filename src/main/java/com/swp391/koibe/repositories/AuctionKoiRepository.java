package com.swp391.koibe.repositories;

import com.swp391.koibe.models.AuctionKoi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionKoiRepository extends JpaRepository<AuctionKoi, Long> {
    List<AuctionKoi> getAuctionKoiByAuctionId(Long auctionId);

}
