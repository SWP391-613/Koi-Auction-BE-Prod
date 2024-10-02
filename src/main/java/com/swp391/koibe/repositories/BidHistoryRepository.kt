package com.swp391.koibe.repositories

import com.swp391.koibe.models.Bid
import org.springframework.data.jpa.repository.JpaRepository

interface BidHistoryRepository : JpaRepository<Bid, Long> {
    fun findAllByAuctionKoiId(auctionKoiId: Long): List<Bid>
    fun existsByAuctionKoiIdAndBidderId(auctionKoiId: Long, bidderId: Long): Boolean
}
