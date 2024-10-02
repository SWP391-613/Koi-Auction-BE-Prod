package com.swp391.koibe.repositories

import com.swp391.koibe.models.Refund
import org.springframework.data.jpa.repository.JpaRepository

interface RefundRepository : JpaRepository<Refund?, Long?>
