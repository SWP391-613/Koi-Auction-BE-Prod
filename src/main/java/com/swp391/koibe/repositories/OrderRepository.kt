package com.swp391.koibe.repositories

import com.swp391.koibe.enums.OrderStatus
import com.swp391.koibe.models.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long>{
    fun findByUserId(userId: Long): List<Order>;
    @Query("SELECT o FROM Order o WHERE o.active = true AND (:keyword IS NULL OR :keyword = '' OR " +
            "o.firstName LIKE %:keyword% " +
            "OR o.lastName LIKE %:keyword% " +
            "OR o.address LIKE %:keyword% " +
            "OR o.note LIKE %:keyword% " +
            "OR o.email LIKE %:keyword%)")
    fun findByKeyword(@Param("keyword") keyword: String?, pageable: Pageable): Page<Order>
    fun findByStatus(status: OrderStatus): List<Order>
    fun findByUserIdAndStatus(userId: Long, status: OrderStatus, pageable: Pageable): Page<Order>

}
