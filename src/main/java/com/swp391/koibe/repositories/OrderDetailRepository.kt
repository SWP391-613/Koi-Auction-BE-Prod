package com.swp391.koibe.repositories

import com.swp391.koibe.models.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository : JpaRepository<OrderDetail?, Long?> {
    fun findByOrderId(orderId: Long?): List<OrderDetail?>?
}
