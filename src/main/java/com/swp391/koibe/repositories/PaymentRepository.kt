package com.swp391.koibe.repositories

import com.swp391.koibe.models.Order
import com.swp391.koibe.models.Payment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findByOrder(order: Order): Optional<Payment>?
}

