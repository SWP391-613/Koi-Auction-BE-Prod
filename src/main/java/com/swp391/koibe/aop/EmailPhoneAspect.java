package com.swp391.koibe.aop;

import com.swp391.koibe.enums.UserStatus;
import com.swp391.koibe.exceptions.UserHasBeenBannedException;
import com.swp391.koibe.exceptions.UserHasBeenVerifiedException;
import com.swp391.koibe.exceptions.UserNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailPhoneAspect {

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Before("execution(* com.swp391.koibe.controllers.MailController.*(..)) && args(toEmail,..)")
    public void checkValidEmail(JoinPoint joinPoint, String toEmail) {
        User user = userRepository.findByEmail(toEmail)
            .orElseThrow(() -> new UserNotFoundException(toEmail));
        if (user.getStatus() == UserStatus.BANNED) {
            throw new UserHasBeenBannedException(toEmail);
        }
        if(user.getStatus() == UserStatus.VERIFIED){
            throw new UserHasBeenVerifiedException(toEmail);
        }
        request.setAttribute("validatedEmail", user);
    }

}