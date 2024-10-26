package com.swp391.koibe.services.otp;

import com.swp391.koibe.models.Otp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OtpServiceTest {

    @MockBean
    private OtpService otpService;

    @BeforeEach
    void setUp() {
        Mockito.reset(otpService);
    }

    @Test
    void createOtp() {

        Otp newOtp = Otp.builder()
            .email("test@gmail.com")
            .otp("123456")
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(newOtp);

        //assert the size of otpService is 1
        Mockito.verify(otpService, Mockito.times(1)).createOtp(newOtp);
    }

    @Test
    void disableOtp() {
    }

    @Test
    void getOtpByEmailOtp() {
    }

    @Test
    void setOtpExpired() {
    }
}