package com.swp391.koibe.repositories

import com.swp391.koibe.models.Auction
import com.swp391.koibe.models.AuctionKoi
import org.springframework.data.jpa.repository.JpaRepository

interface AuctionRepository : JpaRepository<Auction, Long> {
    fun findAllByStatus(status: String): List<AuctionKoi>
    fun existsByTitle(title: String): Boolean
}
