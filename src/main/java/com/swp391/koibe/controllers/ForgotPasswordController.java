package com.swp391.koibe.controllers;

import com.swp391.koibe.constants.EmailSubject;
import com.swp391.koibe.models.User;
import com.swp391.koibe.enums.EmailCategoriesEnum;
import com.swp391.koibe.responses.ForgotPasswordResponse;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.services.mail.MailSenderService;
import com.swp391.koibe.utils.OTPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${api.prefix}/forgotpassword")
@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final MailSenderService mailSenderService;

    @PutMapping("")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestParam @Validated String email_phone) {
        User user = (User) request.getAttribute("validatedAccount");

        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);

        if(user.getEmail() != null){
            mailSenderService.sendNewMail(user.getEmail(), EmailSubject.subjectGreeting(name),
                EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                context);
        } else {
            mailSenderService.sendNewMail(user.getPhoneNumber(), EmailSubject.subjectGreeting(name),
                EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                context);
        }

        ForgotPasswordResponse response = new ForgotPasswordResponse("Forgot password sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
