package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuctionParticipantRepository : JpaRepository<AuctionParticipant, Long> {
    fun findByAuctionIdAndUserId(auctionId: Long, userId: Long): Optional<AuctionParticipant>
}
