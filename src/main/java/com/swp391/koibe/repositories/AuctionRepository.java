package com.swp391.koibe.repositories;

import com.swp391.koibe.models.Auction;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.responses.AuctionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<AuctionKoi> findAllByStatus(String status);
    boolean existsByTitle(String title);
}
