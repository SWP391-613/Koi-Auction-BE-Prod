package com.swp391.koibe.repositories

import com.swp391.koibe.models.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long>
