//package com.swp391.koibe.aop;
//
//import com.swp391.koibe.enums.UserStatus;
//import com.swp391.koibe.models.User;
//import com.swp391.koibe.exceptions.UserHasBeenBannedException;
//import com.swp391.koibe.exceptions.UserNotFoundException;
//import com.swp391.koibe.repositories.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class EmailPhoneAspect {
//
//    private final UserRepository userRepository;
//    private final HttpServletRequest request;
//
//    @Before("execution(* com.swp391.koibe.controllers.MailController.*(..)) && args(toEmail,..)")
//    public void checkIfEmailExists(JoinPoint joinPoint, String toEmail) {
//        User user = userRepository.findByEmail(toEmail)
//            .orElseThrow(() -> new UserNotFoundException(toEmail));
//        if (user.getStatus() == UserStatus.BANNED) {
//            throw new UserHasBeenBannedException(toEmail);
//        }
//        request.setAttribute("validatedEmail", user);
//    }
//
//    @Before("execution(* com.swp391.koibe.controllers.PhoneController.*(..)) && args(toPhone,..)")
//    public void checkIfPhoneExists(JoinPoint joinPoint, String toPhone) {
//        User user = userRepository.findByPhoneNumber(toPhone)
//            .orElseThrow(() -> new UserNotFoundException(toPhone));
//        if (user.getStatus() == UserStatus.BANNED) {
//            throw new UserHasBeenBannedException(toPhone);
//        }
//        request.setAttribute("validatedPhone", user);
//    }
//
//    // Combined pointcut for both UserController and ForgotPasswordController
//    @Before("execution(* com.swp391.koibe.controllers.UserController.*(..)) && args(email_phone,."
//        + ".) || "
//        + "execution(* com.swp391.koibe.controllers.ForgotPasswordController.*(..)) && args"
//        + "(email_phone,..)")
//    public void checkIfAccountExists(JoinPoint joinPoint, String email_phone) {
//        checkAccount(email_phone);
//    }
//
//    private void checkAccount(String email_phone) {
//        User user = userRepository.findByEmail(email_phone)
//            .orElseThrow(() -> new UserNotFoundException(email_phone));
//        if (user.getStatus() == UserStatus.BANNED) {
//            throw new UserHasBeenBannedException(email_phone);
//        }
//        request.setAttribute("validatedAccount", user);
//    }
//
//}