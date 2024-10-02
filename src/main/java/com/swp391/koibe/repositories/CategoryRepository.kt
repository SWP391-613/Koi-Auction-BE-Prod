package com.swp391.koibe.repositories

import com.swp391.koibe.models.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category?, Int?> {
    fun findByName(name: String?): Category?
}
