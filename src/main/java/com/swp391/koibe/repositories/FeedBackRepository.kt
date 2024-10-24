package com.swp391.koibe.repositories

import com.swp391.koibe.models.Feedback
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface FeedBackRepository : JpaRepository<Feedback, Long> {
    fun existsByUserIdAndOrderId(userId: Long, orderId: Long): Boolean
    fun findByOrderId(orderId: Long): Optional<Feedback>
}
