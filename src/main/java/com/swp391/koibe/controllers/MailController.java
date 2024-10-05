package com.swp391.koibe.controllers;

import com.swp391.koibe.constants.EmailSubject;
import com.swp391.koibe.enums.EmailBlockReasonEnum;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.enums.UserStatus;
import com.swp391.koibe.models.Otp;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.MailResponse;
import com.swp391.koibe.services.mail.MailService;
import com.swp391.koibe.services.otp.IOtpService;
import com.swp391.koibe.utils.OTPUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${api.prefix}/mail")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final HttpServletRequest request;
    private final IOtpService otpService;

    //api: /otp/send?type=email&recipient=abc@gmail
    public ResponseEntity<MailResponse> sendOtp(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");

        if(user.getStatus() == UserStatus.VERIFIED){
            return new ResponseEntity<>(new MailResponse("Email already verified"), HttpStatus.BAD_REQUEST);
        }

        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailService.sendMail(toEmail, EmailSubject.subjectGreeting(name),
                             EmailCategoriesEnum.OTP.getType(),
                             context);
        MailResponse response = new MailResponse("Mail sent successfully");

        Otp otpEntity = Otp.builder()
            .email(toEmail)
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/block")
    ResponseEntity<MailResponse> sendBlockAccount(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");
        Context context = new Context();
        context.setVariable("reason", EmailBlockReasonEnum.ABUSE.getReason());
        mailService.sendMail(toEmail, EmailSubject.subjectBlockEmail(user.getFirstName()),
                             EmailCategoriesEnum.BLOCK_ACCOUNT.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/forgotPassword")
    ResponseEntity<MailResponse> sendForgotPassword(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");
        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailService.sendMail(toEmail, EmailSubject.subjectGreeting(name), EmailCategoriesEnum.FORGOT_PASSWORD.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}