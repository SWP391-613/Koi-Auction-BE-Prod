package com.swp391.koibe.repositories

import com.swp391.koibe.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByEmail(email: String?): Optional<User?>?
    fun findByPhoneNumber(phoneNumber: String?): Optional<User?>?
    fun findByRoleName(roleName: String?, pageable: Pageable?): Page<User?>?
    fun existsByPhoneNumber(phoneNumber: String?): Boolean
    fun existsByEmail(email: String?): Boolean
}
