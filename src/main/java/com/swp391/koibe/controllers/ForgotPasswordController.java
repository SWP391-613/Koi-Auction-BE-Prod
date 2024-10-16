package com.swp391.koibe.controllers;

import com.swp391.koibe.dtos.UpdatePasswordDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.ForgotPasswordResponse;
import com.swp391.koibe.services.forgotpassword.IForgotPasswordService;
import com.swp391.koibe.services.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "${api.prefix}/forgot-password")
@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final HttpServletRequest request;
private final IForgotPasswordService forgotPasswordService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(
        @Validated @RequestParam String toEmail
    ) throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail"); //get from aop

        try{
            forgotPasswordService.sendEmailOtp(user);

            ForgotPasswordResponse response =
                new ForgotPasswordResponse("Forgot password email sent successfully to " + user.getEmail());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            if(e instanceof MessagingException){
                log.error("Error sending email", e.getCause());
                throw e;
            }
            throw e;
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updatePassword(
        @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO,
        BindingResult result
    ){
        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }

        try{
            userService.updatePassword(updatePasswordDTO);
            return ResponseEntity.ok("Password updated successfully");
        }catch (Exception e){
            log.error("Error updating password", e.getCause());
            return ResponseEntity.badRequest().body("Error updating password");
        }


    }

}
