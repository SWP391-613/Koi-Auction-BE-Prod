package com.swp391.koibe.repositories

import com.swp391.koibe.enums.EKoiStatus
import com.swp391.koibe.models.Koi
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface KoiRepository : JpaRepository<Koi, Long> {
    fun existsByName(name: String): Boolean
    fun findByOwnerId(ownerId: Long, pageable: Pageable): Page<Koi>
    fun findByOwnerIdAndStatus(owner_id: Long, status: EKoiStatus, pageable: Pageable): Page<Koi>
    fun findByStatus(status: EKoiStatus, pageable: Pageable): Page<Koi>
}
