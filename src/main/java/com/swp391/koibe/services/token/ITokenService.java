package com.swp391.koibe.services.token;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Token;
import com.swp391.koibe.models.User;
import org.springframework.stereotype.Service;

@Service

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
    void deleteToken(String token, User user) throws DataNotFoundException;
    Token findUserByToken(String token) throws DataNotFoundException;
}
