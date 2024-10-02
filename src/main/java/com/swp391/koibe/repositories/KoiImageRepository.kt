package com.swp391.koibe.repositories

import com.swp391.koibe.models.KoiImage
import org.springframework.data.jpa.repository.JpaRepository

interface KoiImageRepository : JpaRepository<KoiImage?, Long?> {
    fun findByKoiId(koiId: Long?): List<KoiImage?>?
}
