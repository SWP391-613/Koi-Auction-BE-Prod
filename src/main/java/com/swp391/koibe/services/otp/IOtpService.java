package com.swp391.koibe.services.otp;

import com.swp391.koibe.models.Otp;
import java.util.Optional;

public interface IOtpService {

    Otp createOtp(Otp otp);
    void disableOtp(long id);
    Optional<Otp> getOtpByEmail(String email);
}
