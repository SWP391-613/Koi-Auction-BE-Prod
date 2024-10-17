package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionKoi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AuctionKoiRepository : JpaRepository<AuctionKoi, Long> {
    fun findAuctionKoiByAuctionId(auctionId: Long): List<AuctionKoi>
    fun findAuctionKoiByAuctionIdAndKoiId(auctionId: Long, koiId: Long): AuctionKoi
}
