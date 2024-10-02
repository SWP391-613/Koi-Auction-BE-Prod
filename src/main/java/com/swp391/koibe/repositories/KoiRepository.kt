package com.swp391.koibe.repositories

import com.swp391.koibe.models.Koi
import org.springframework.data.jpa.repository.JpaRepository

interface KoiRepository : JpaRepository<Koi?, Long?> {
    fun existsByName(name: String?): Boolean
}
