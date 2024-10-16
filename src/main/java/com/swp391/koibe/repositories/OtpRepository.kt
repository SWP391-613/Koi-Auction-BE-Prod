package com.swp391.koibe.repositories

import com.swp391.koibe.models.Otp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*


interface OtpRepository : JpaRepository<Otp, Long> {
    fun findByEmail(email: String): Optional<Otp>

    //findByEmailAndOtp(String email, int otp);
    fun findByEmailAndOtp(email: String, otp: String): Optional<Otp>

    @Modifying
    @Query("UPDATE Otp o SET o.isExpired = true WHERE o.expiredAt < :now AND o.isExpired = false")
    fun updateExpiredOtps(now: LocalDateTime?)
}
