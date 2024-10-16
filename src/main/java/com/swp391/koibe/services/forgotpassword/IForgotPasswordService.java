package com.swp391.koibe.services.forgotpassword;

import com.swp391.koibe.models.User;
import jakarta.mail.MessagingException;

public interface IForgotPasswordService {

    void sendEmailOtp(User existingUser) throws MessagingException;

}
