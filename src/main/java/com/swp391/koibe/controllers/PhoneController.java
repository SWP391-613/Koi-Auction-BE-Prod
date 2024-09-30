package com.swp391.koibe.controllers;

import com.swp391.koibe.enums.UserStatus;
import com.swp391.koibe.models.User;
import com.swp391.koibe.enums.UserStatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneController {

    @Autowired
    private HttpServletRequest request;

    public ResponseEntity<?> sendPhoneOtp(@RequestParam String toPhone) {

        User user = (User) request.getAttribute("validatedPhone");

        if(user.getStatus() == UserStatus.VERIFIED){
            return ResponseEntity.badRequest().body("Phone number already verified");
        }

        return ResponseEntity.ok("OTP sent successfully");
    }

}