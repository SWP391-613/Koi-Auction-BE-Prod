package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.custom.RetryAndBlock;
import com.swp391.koibe.dtos.UpdateUserDTO;
import com.swp391.koibe.dtos.UserLoginDTO;
import com.swp391.koibe.dtos.UserRegisterDTO;
import com.swp391.koibe.dtos.VerifyUserDTO;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Token;
import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.LoginResponse;
import com.swp391.koibe.responses.LogoutResponse;
import com.swp391.koibe.responses.OtpResponse;
import com.swp391.koibe.responses.RegisterResponse;
import com.swp391.koibe.responses.UpdateUserResponse;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.services.token.TokenService;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.MessageKeys;
import com.swp391.koibe.utils.ResponseUtils;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;
    private final LocalizationUtils localizationUtils;
    private final TokenService tokenService;

    @Timed(
        value = "custom.login.requests",
        extraTags = {"uri", "/api/v1/users/login"},
        description = "Track login request count")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody @Valid UserLoginDTO userLoginDTO,
        BindingResult result,
        HttpServletRequest request) {

        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }

        try {
            String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            String userAgent = request.getHeader("User-Agent");
            User userDetail = userService.getUserDetailsFromToken(token);
            Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));
            log.info("User logged in successfully");
            return ResponseEntity.ok(LoginResponse.builder()
                                         .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                                         .token(jwtToken.getToken())
                                         .tokenType(jwtToken.getTokenType())
                                         .refreshToken(jwtToken.getRefreshToken())
                                         .username(userDetail.getUsername())
                                         .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                                         .id(userDetail.getId())
                                         .build());
        } catch (Exception e) {
            return ResponseUtils.loginFailed(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()));
        }

    }

    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("mobile");
    }

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(DTOConverter.convertToUserDTO(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Timed(
        value = "custom.register.requests",
        extraTags = {"uri", "/api/v1/users/register"},
        description = "Track register request count")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
        @RequestBody @Valid UserRegisterDTO userRegisterDTO,
        BindingResult result) {

        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userRegisterDTO.password().equals(userRegisterDTO.confirmPassword())) {
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.createUser(userRegisterDTO);
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUserResponse(DTOConverter.convertToUserDTO(user));
            log.info("New user registered successfully");
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<?> updateUserDetails(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserDTO updatedUserDTO,
        @RequestHeader("Authorization") String authorizationHeader,
        BindingResult result
    ){
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

            return ResponseEntity.badRequest().body(errorMessages);
        }

        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        try{
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            // Ensure that the user making the request matches the user being updated
            if (!Objects.equals(user.getId(), userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User updatedUser = userService.updateUser(userId, updatedUserDTO);
            updateUserResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_USER_SUCCESSFULLY));
            updateUserResponse.setUserResponse(DTOConverter.convertToUserDTO(updatedUser));
            return ResponseEntity.ok(updateUserResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/block/{userId}/{active}")
    public ResponseEntity<String> blockOrEnable(
        @Valid @PathVariable long userId,
        @Valid @PathVariable int active
    ) {
        try {
            userService.blockOrEnable(userId, active > 0);
            String message = active > 0 ? "Successfully enabled the user." : "Successfully blocked the user.";
            return ResponseEntity.ok().body(message);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/verify/{otp}")
    public ResponseEntity<OtpResponse> verifiedUser(
        @PathVariable int otp,
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        OtpResponse otpResponse = new OtpResponse();
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            userService.verifyOtp(user.getId(), String.valueOf(otp));
            otpResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.VERIFY_USER_SUCCESSFULLY));
            return ResponseEntity.ok().body(otpResponse);
        } catch (Exception e){
            otpResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.VERIFY_USER_FAILED));
            otpResponse.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(otpResponse);
        }
    }

    @Timed(
        value = "custom.verify.requests",
        extraTags = {"uri", "/api/v1/users/verify"},
        description = "Track verify request count")
    @RetryAndBlock(maxAttempts = 3, blockDurationSeconds = 3600, maxDailyAttempts = 6)
    @PostMapping("/verify")
    public ResponseEntity<OtpResponse> verifiedUserNotLogin(
        @Valid @RequestBody VerifyUserDTO verifyUserDTO,
        BindingResult result
    ) {
        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }

        OtpResponse otpResponse = new OtpResponse();
        try {
            User user = userService.getUserByEmail(verifyUserDTO.email());
            userService.verifyOtp(user.getId(), verifyUserDTO.otp());
            otpResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.VERIFY_USER_SUCCESSFULLY));
            return ResponseEntity.ok().body(otpResponse);
        } catch (Exception e){
            otpResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.VERIFY_USER_FAILED));
            otpResponse.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(otpResponse);
        }
    }

    @Timed(
        value = "custom.logout.requests",
        extraTags = {"uri", "/api/v1/users/logout"},
        description = "Track logout request count")
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            tokenService.deleteToken(extractedToken, user); //revoke token
            logoutResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.LOGOUT_SUCCESSFULLY));
            return ResponseEntity.ok().body(logoutResponse);
        } catch (Exception e) {
            logoutResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.LOGOUT_FAILED));
            logoutResponse.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(logoutResponse);
        }
    }

}
