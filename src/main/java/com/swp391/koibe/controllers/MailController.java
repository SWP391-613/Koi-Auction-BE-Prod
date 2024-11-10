package com.swp391.koibe.controllers;

import com.swp391.koibe.constants.EmailSubject;
import com.swp391.koibe.annotations.SkipEmailValidation;
import com.swp391.koibe.enums.EUpdateRole;
import com.swp391.koibe.enums.EmailBlockReasonEnum;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.models.Otp;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.MailResponse;
import com.swp391.koibe.services.mail.IMailService;
import com.swp391.koibe.services.otp.IOtpService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.OTPUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${api.prefix}/mail")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;
    private final HttpServletRequest request;
    private final IOtpService otpService;
    private final IUserService userService;

    //GET: localhost:4000/api/v1/mail/update-role?updateRole=STAFF
    @GetMapping("/update-role")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @SkipEmailValidation
    public ResponseEntity<?> sendOtp(
        @RequestHeader("Authorization") String authorizationHeader,
        @RequestParam EUpdateRole updateRole
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("sendFromEmail", user.getEmail());
        context.setVariable("role", updateRole);
        mailService.sendMail("hoangclw@gmail.com", EmailSubject.subjectRequestUpdateRole(), EmailCategoriesEnum.UPDATE_ROLE.getType(), context);
        mailService.sendMail(user.getEmail(), EmailSubject.subjectRequestUpdateRole(), EmailCategoriesEnum.PROCESSING_UPDATE_ROLE.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //api: /otp/send?type=email&recipient=abc@gmail
    public ResponseEntity<MailResponse> sendOtp(@RequestParam String toEmail) throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail");

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
    ResponseEntity<MailResponse> sendBlockAccount(@RequestParam String toEmail) throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail");
        Context context = new Context();
        context.setVariable("reason", EmailBlockReasonEnum.ABUSE.getReason());
        mailService.sendMail(toEmail, EmailSubject.subjectBlockEmail(user.getFirstName()),
                             EmailCategoriesEnum.BLOCK_ACCOUNT.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/forgotPassword")
    ResponseEntity<MailResponse> sendForgotPassword(@RequestParam String toEmail) throws MessagingException {
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