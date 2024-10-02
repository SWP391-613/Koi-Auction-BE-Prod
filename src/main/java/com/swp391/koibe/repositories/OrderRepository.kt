package com.swp391.koibe.repositories

import com.swp391.koibe.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order?, Long?>
