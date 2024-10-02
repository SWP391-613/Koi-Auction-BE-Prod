package com.swp391.koibe.repositories

import com.swp391.koibe.models.Otp
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OtpRepository : JpaRepository<Otp, Long> {
    fun findByEmail(email: String): Optional<Otp>

    //findByEmailAndOtp(String email, int otp);
    fun findByEmailAndOtp(email: String, otp: String): Optional<Otp>
}
