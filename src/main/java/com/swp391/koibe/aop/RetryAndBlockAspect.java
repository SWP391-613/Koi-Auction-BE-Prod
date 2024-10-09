//package com.swp391.koibe.aop;
//
//import com.swp391.koibe.components.JwtTokenUtils;
//import com.swp391.koibe.configs.EmailConfig;
//import com.swp391.koibe.custom.RetryAndBlock;
//import com.swp391.koibe.enums.EmailCategoriesEnum;
//import com.swp391.koibe.exceptions.base.DataNotFoundException;
//import com.swp391.koibe.models.User;
//import com.swp391.koibe.services.mail.IMailService;
//import com.swp391.koibe.services.user.IUserService;
//import jakarta.servlet.http.HttpServletRequest;
//import java.time.Duration;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.thymeleaf.context.Context;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class RetryAndBlockAspect {
//
//    private final Map<String, Integer> attemptCounter = new ConcurrentHashMap<>();
//    private final Map<String, Instant> blockedIPs = new ConcurrentHashMap<>();
//    private final Map<String, Integer> dailyAttemptCounter = new ConcurrentHashMap<>();
//    private final Map<String, LocalDate> lastAttemptDate = new ConcurrentHashMap<>();
//    private final IMailService mailService;
//    private final IUserService userService;
//    private final JwtTokenUtils jwtTokenUtils;
//    private final EmailConfig emailConfig;
//
//    @Around("@annotation(retryAndBlock)")
//    public Object handleRetryAndBlock(ProceedingJoinPoint joinPoint, RetryAndBlock retryAndBlock)
//        throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = request.getRemoteAddr();
//        String endpoint = request.getRequestURI();
//        String key = ip + ":" + endpoint;
//
//        // Get the current user's email
//        String userEmail = getCurrentUserEmail(request);
//
//        if (isBlocked(key)) {
//            Duration remainingBlockTime = getRemainingBlockTime(key);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(String.format(
//                    "Your access is temporarily blocked. Please try again in %d minutes.",
//                    remainingBlockTime.toMinutes()));
//        }
//
//        updateDailyAttemptCounter(key, retryAndBlock.maxDailyAttempts());
//
//        int attempts = attemptCounter.getOrDefault(key, 0);
//        int dailyAttempts = dailyAttemptCounter.getOrDefault(key, 0);
//
//        if (dailyAttempts >= retryAndBlock.maxDailyAttempts()) {
//            handleServiceBlock(ip, endpoint, userEmail);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(
//                    "You have exceeded the maximum daily attempts. Your account has been flagged for service.");
//        }
//
//        if (attempts >= retryAndBlock.maxAttempts()) {
//            handleBlockedRequest(key, ip, endpoint, retryAndBlock.blockDurationSeconds(),
//                                 userEmail);
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
//                .body(String.format(
//                    "Too many failed attempts. This request is now blocked for %d minutes.",
//                    retryAndBlock.blockDurationSeconds() / 60));
//        }
//
//        Object result = joinPoint.proceed();
//
//        if (result instanceof ResponseEntity) {
//            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
//            if (responseEntity.getStatusCode() != HttpStatus.OK) {
//                attempts++;
//                attemptCounter.put(key, attempts);
//                if (attempts >= retryAndBlock.maxAttempts()) {
//                    handleBlockedRequest(key, ip, endpoint, retryAndBlock.blockDurationSeconds(),
//                                         userEmail);
//                    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
//                        .body(String.format(
//                            "Too many failed attempts. This request is now blocked for %d minutes.",
//                            retryAndBlock.blockDurationSeconds() / 60));
//                }
//            } else {
//                // Reset counter on successful attempt
//                attemptCounter.remove(key);
//            }
//        }
//
//        return result;
//    }
//
//    private void updateDailyAttemptCounter(String key, int maxDailyAttempts) {
//        LocalDate today = LocalDate.now(ZoneId.systemDefault());
//        LocalDate lastAttempt = lastAttemptDate.getOrDefault(key, today.minusDays(1));
//
//        if (!lastAttempt.equals(today)) {
//            dailyAttemptCounter.put(key, 1);
//        } else {
//            dailyAttemptCounter.merge(key, 1, Integer::sum);
//        }
//
//        lastAttemptDate.put(key, today);
//    }
//
//    private boolean isBlocked(String key) {
//        Instant blockedUntil = blockedIPs.get(key);
//        if (blockedUntil != null) {
//            if (Instant.now().isBefore(blockedUntil)) {
//                return true;
//            } else {
//                blockedIPs.remove(key);
//                attemptCounter.remove(key);  // Reset attempt counter after block expires
//                return false;
//            }
//        }
//        return false;
//    }
//
//    private Duration getRemainingBlockTime(String key) {
//        Instant blockedUntil = blockedIPs.get(key);
//        if (blockedUntil != null) {
//            return Duration.between(Instant.now(), blockedUntil);
//        }
//        return Duration.ZERO;
//    }
//
//    private void handleBlockedRequest(String key, String ip, String endpoint,
//                                      long blockDurationSeconds, String userEmail) {
//        log.warn(
//            "IP {} (User: {}) has been blocked for endpoint {} due to too many failed attempts", ip,
//            userEmail, endpoint);
//
//        Context context = new Context();
//        context.setVariable("ip", ip);
//        context.setVariable("endpoint", endpoint);
//        context.setVariable("blockDuration", blockDurationSeconds / 60);
//        context.setVariable("userEmail", userEmail);
//
//        for(String managerEmail : emailConfig.getManagerEmailList()){
//            mailService.sendMail(
//                managerEmail,
//                "Account Blocked Alert",
//                EmailCategoriesEnum.RETRY_BLOCK_ALERT.getType(),
//                context
//            );
//        }
//
//        blockedIPs.put(key, Instant.now().plusSeconds(blockDurationSeconds));
//    }
//
//    private void handleServiceBlock(String ip, String endpoint, String userEmail)
//        throws DataNotFoundException {
//        log.warn(
//            "IP {} (User: {}) has exceeded daily attempts for endpoint {}. Flagging for service.",
//            ip, userEmail, endpoint);
//
//        Context context = new Context();
//        context.setVariable("ip", ip);
//        context.setVariable("endpoint", endpoint);
//        context.setVariable("userEmail", userEmail);
//
//        for(String managerEmail : emailConfig.getManagerEmailList()) {
//            mailService.sendMail(
//                managerEmail,
//                "Account Flagged for Service",
//                EmailCategoriesEnum.RETRY_BLOCK_ALERT.getType(),
//                context
//            );
//        }
//
//        User user = userService.getUserByEmail(userEmail);
//        if (user != null) {
//            try {
//                userService.bannedUser(user.getId());
//            } catch (DataNotFoundException e) {
//                throw e;
//            }
//        }
//    }
//
//    private String getCurrentUserEmail(HttpServletRequest request) {
//        // This method should be implemented to get the current user's email
//        // It could be from a session, a JWT token, or any other authentication mechanism you're using
//        // For example:
//        // return jwtTokenUtil.getEmailFromToken(request.getHeader("Authorization"));
//        // Or:
//        // return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//
//        // Placeholder implementation:
//        return jwtTokenUtils.extractEmail(request.getHeader("Authorization"));  // Replace this with actual
//        // implementation
//    }
//
//}