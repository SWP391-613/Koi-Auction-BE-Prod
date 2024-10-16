package com.swp391.koibe.controllers;

import com.swp391.koibe.constants.EmailSubject;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.models.Otp;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.responses.ForgotPasswordResponse;
import com.swp391.koibe.services.mail.IMailService;
import com.swp391.koibe.services.otp.IOtpService;
import com.swp391.koibe.utils.OTPUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${api.prefix}/forgot-password")
@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final HttpServletRequest request;
    private final IMailService mailService;
    private final IOtpService otpService;

    @GetMapping("")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(
        @Validated @RequestParam String toEmail
    ) throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail"); //get from aop

        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", user.getFirstName());
        context.setVariable("otp", otp);

        mailService.sendMail(user.getEmail(),
                             EmailSubject.subjectForgotPassword(user.getFirstName()),
                             EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                             context);

        Otp otpEntity = Otp.builder()
            .email(user.getEmail())
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);

        ForgotPasswordResponse response = new ForgotPasswordResponse("Forgot password email sent "
                                                                         + "successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
