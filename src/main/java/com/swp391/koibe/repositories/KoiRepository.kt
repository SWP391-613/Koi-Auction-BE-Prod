package com.swp391.koibe.repositories

import com.swp391.koibe.enums.EKoiStatus
import com.swp391.koibe.models.Koi
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface KoiRepository : JpaRepository<Koi, Long> {
    fun existsByName(name: String): Boolean
    fun findByOwnerId(ownerId: Long, pageable: Pageable): Page<Koi>
    fun findByOwnerIdAndStatus(owner_id: Long, status: EKoiStatus, pageable: Pageable): Page<Koi>
    fun findByStatus(status: EKoiStatus, pageable: Pageable): Page<Koi>

    //SELECT koi data is display in existing auction
    @Query("SELECT k FROM Koi k INNER JOIN AuctionKoi ak ON k.id = ak.koi.id WHERE k.status = 'VERIFIED' AND " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "k.name LIKE CONCAT('%', :keyword, '%') " +
            "OR k.description LIKE CONCAT('%', :keyword, '%') " +
            "OR k.sex LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.length AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.age AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.price AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.owner as string) LIKE CONCAT('%', :keyword, '%'))")
    fun findByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<Koi>
}
