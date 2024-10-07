package com.swp391.koibe.repositories

import com.swp391.koibe.models.Koi
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface KoiRepository : JpaRepository<Koi, Long> {
    fun existsByName(name: String): Boolean
    fun findByOwnerId(ownerId: Long, pageable: Pageable): Page<Koi>
}
