package com.swp391.koibe.repositories

import com.swp391.koibe.enums.EAuctionStatus
import com.swp391.koibe.models.Auction
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface AuctionRepository : JpaRepository<Auction, Long> {
    fun findAllByStatus(status: EAuctionStatus): List<Auction>
    fun existsByTitle(title: String): Boolean
    fun getAuctionsByStartTimeAfter(startTime: LocalDateTime): Set<Auction>
    fun getAuctionsByEndTimeBefore(endTime: LocalDateTime): Set<Auction>
    fun getAuctionsByStartTimeBeforeAndEndTimeAfter(startTime: LocalDateTime, endTime: LocalDateTime): Set<Auction>
    fun getAuctionsByStatus(status: EAuctionStatus): Set<Auction>
    fun findByAuctioneerId(auctioneerId: Long): List<Auction>
}
