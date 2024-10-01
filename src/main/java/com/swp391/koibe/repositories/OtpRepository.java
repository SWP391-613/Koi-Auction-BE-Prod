package com.swp391.koibe.repositories;

import com.swp391.koibe.models.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmail(String email);
    //findByEmailAndOtp(String email, int otp);
    Optional<Otp> findByEmailAndOtp(String email, String otp);

}
