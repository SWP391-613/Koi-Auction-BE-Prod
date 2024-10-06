package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionKoi
import org.springframework.data.jpa.repository.JpaRepository

interface AuctionKoiRepository : JpaRepository<AuctionKoi, Long> {
    fun getAuctionKoiByAuctionId(auctionId: Long): List<AuctionKoi>
    fun getAuctionKoiByAuctionIdAndKoiId(auctionId: Long, koiId: Long): AuctionKoi
}
