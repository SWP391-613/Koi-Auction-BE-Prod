package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuctionParticipantRepository : JpaRepository<AuctionParticipant, Long> {
    fun getAuctionParticipantByAuctionIdAndUserId(auctionId: Long, userId: Long): AuctionParticipant?
    fun getAuctionParticipantsByAuctionId(auctionId: Long): List<AuctionParticipant>
}
