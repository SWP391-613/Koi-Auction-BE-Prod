package com.swp391.koibe.repositories

import com.swp391.koibe.models.Token
import com.swp391.koibe.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token?, Long?> {
    fun findByUser(user: User?): List<Token?>?
    fun findByToken(token: String?): Token?
    fun findByRefreshToken(token: String?): Token?
}

