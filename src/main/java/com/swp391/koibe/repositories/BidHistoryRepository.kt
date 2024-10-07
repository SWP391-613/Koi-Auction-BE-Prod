package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionKoi
import com.swp391.koibe.models.Bid
import org.springframework.data.jpa.repository.JpaRepository

interface BidHistoryRepository : JpaRepository<Bid, Long> {
    fun getAllByAuctionKoiIdAndBidderId(auctionKoiId: Long, bidderId: Long): ArrayList<Bid>
    fun existsByAuctionKoiIdAndBidderId(auctionKoiId: Long, bidderId: Long): Boolean
    fun getAllByAuctionKoiId(auctionKoiId: Long): ArrayList<Bid>
}
