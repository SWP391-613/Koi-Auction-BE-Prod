package com.swp391.koibe.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.OAuth2DTO;
import com.swp391.koibe.exceptions.InvalidParamException;
import com.swp391.koibe.responses.LoginResponse;
import com.swp391.koibe.responses.OAuth2Payload;
import com.swp391.koibe.services.user.IUserService;
import com.swp391.koibe.utils.MessageKeys;
import com.swp391.koibe.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/oauth2")
public class OAuth2Controller {

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;
    private final LocalizationUtils localizationUtils;
    private final IUserService userService;

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> authenticateGoogle(@RequestBody OAuth2DTO tokenRequest) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

            GoogleIdToken idToken = verifier.verify(tokenRequest.token());
            if (idToken == null) {
                throw new InvalidParamException("Invalid token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            OAuth2Payload oAuth2Payload = OAuth2Payload.builder()
                .sub(payload.getSubject())
                .email(payload.getEmail())
                .name(String.valueOf(payload.get("name")))
                .picture(String.valueOf(payload.get("picture")))
                .build();

            String token = userService.loginOrRegisterGoogle(
                oAuth2Payload.getEmail(),
                oAuth2Payload.getName(),
                oAuth2Payload.getSub(),
                oAuth2Payload.getPicture());

            log.info("Login via Google successfully");
            return ResponseUtils.loginSuccess(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY), token);
        } catch (Exception e) {
            return ResponseUtils.loginFailed(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()));
        }
    }
    @GetMapping("/google-client-id")
    public ResponseEntity<Map<String, String>> getGoogleClientId() {
        Map<String, String> response = new HashMap<>();
        response.put("clientId", clientId);
        System.out.println(clientId);
        return ResponseEntity.ok(response);
    }

}

