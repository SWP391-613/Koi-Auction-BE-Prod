package com.swp391.koibe.repositories

import com.swp391.koibe.models.AuctionParticipant
import org.springframework.data.jpa.repository.JpaRepository

interface AuctionParticipantRepository : JpaRepository<AuctionParticipant?, Long?>
