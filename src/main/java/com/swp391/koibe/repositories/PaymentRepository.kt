package com.swp391.koibe.repositories

import com.swp391.koibe.enums.EPaymentStatus
import com.swp391.koibe.models.Order
import com.swp391.koibe.models.Payment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findByOrder(order: Order): Optional<Payment>?
    fun findPaymentsByUserId(userId: Long, pageable: Pageable): Page<Payment>
    fun findPaymentsByUserIdAndPaymentStatus(userId: Long, status: EPaymentStatus, pageable: Pageable): Page<Payment>

    @Query(
        "SELECT p FROM Payment p WHERE " +
                "p.paymentStatus = :status AND " +
                ":keyword IS NULL OR :keyword = '' OR " +
                "(p.order.user.email LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR p.order.user.phoneNumber LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR p.bankNumber LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                " OR LOWER(CONCAT(p.user.firstName, ' ', p.user.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR CAST(p.user.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')))"
    )
    fun findPaymentsByStatusAndKeyWord(keyword: String?, status: EPaymentStatus, pageable: Pageable): Page<Payment>

    @Query(
        "SELECT p FROM Payment p WHERE " +
                ":keyword IS NULL OR :keyword = '' OR " +
                "(p.order.user.email LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR p.order.user.phoneNumber LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR p.bankNumber LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                " OR LOWER(CONCAT(p.user.firstName, ' ', p.user.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                " OR CAST(p.user.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')))"
    )
    fun findPaymentsByKeyword(keyword: String?, pageable: Pageable): Page<Payment>
}

