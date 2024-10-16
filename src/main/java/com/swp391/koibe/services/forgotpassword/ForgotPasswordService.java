package com.swp391.koibe.services.forgotpassword;

import com.swp391.koibe.constants.EmailSubject;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.models.Otp;
import com.swp391.koibe.models.User;
import com.swp391.koibe.services.mail.IMailService;
import com.swp391.koibe.services.otp.IOtpService;
import com.swp391.koibe.utils.OTPUtils;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService implements IForgotPasswordService{

    private final IMailService mailService;
    private final IOtpService otpService;

    @Override
    public void sendEmailOtp(User existingUser) throws MessagingException {
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", existingUser.getFirstName());
        context.setVariable("otp", otp);

        mailService.sendMail(existingUser.getEmail(),
                             EmailSubject.subjectForgotPassword(existingUser.getFirstName()),
                             EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                             context);

        Otp otpEntity = Otp.builder()
            .email(existingUser.getEmail())
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);
    }
}
