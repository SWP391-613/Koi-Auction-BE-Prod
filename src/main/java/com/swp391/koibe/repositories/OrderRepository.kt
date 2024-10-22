package com.swp391.koibe.repositories

import com.swp391.koibe.enums.OrderStatus
import com.swp391.koibe.models.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserId(userId: Long): List<Order>;

    @Query(
        "SELECT o FROM Order o WHERE o.active = true " +
                "AND (:status IS NULL OR o.status = :status) " +
                "AND (:keyword IS NULL OR :keyword = '' OR " +
                "LOWER(o.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.note) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.email) LIKE LOWER(CONCAT('%', :keyword, '%')))"
    )
    fun findByKeyword(
        @Param("keyword") keyword: String?,
        @Param("status") status: OrderStatus?,
    ): List<Order>

    fun findByStatus(status: OrderStatus): List<Order>
    fun findByUserIdAndStatus(userId: Long, status: OrderStatus, pageable: Pageable): Page<Order>

}
