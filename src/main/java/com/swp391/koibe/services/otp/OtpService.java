package com.swp391.koibe.services.otp;

import com.swp391.koibe.models.Otp;
import com.swp391.koibe.repositories.OtpRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpService implements IOtpService{

    private final OtpRepository otpRepository;

    @Override
    public Otp createOtp(Otp otp) {
        Otp newOtp = Otp.builder()
                .email(otp.getEmail())
                .otp(otp.getOtp())
                .expiredAt(otp.getExpiredAt())
                .isUsed(otp.isUsed())
                .isExpired(otp.isExpired())
                .build();
        return otpRepository.save(newOtp);
    }

    @Override
    public void disableOtp(long id) {
        Otp existingOtp = otpRepository.findById(id).orElse(null);
        if(existingOtp == null) return;
        existingOtp.setExpired(true);
        otpRepository.save(existingOtp);
    }

    @Override
    public Optional<Otp> getOtpByEmail(String email) {
        return otpRepository.findByEmail(email);
    }

}
