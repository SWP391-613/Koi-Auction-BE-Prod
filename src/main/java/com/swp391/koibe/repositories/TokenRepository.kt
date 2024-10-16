package com.swp391.koibe.repositories

import com.swp391.koibe.models.Token
import com.swp391.koibe.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TokenRepository : JpaRepository<Token, Long> {
    fun findByUser(user: User): List<Token>
    fun findByToken(token: String): Token
    fun findByRefreshToken(token: String): Token

    @Modifying
    @Query("UPDATE Token t SET t.expired = true WHERE t.expirationDate < :now AND t.expired = false")
    fun updateExpiredTokens(now: LocalDateTime?)
}

