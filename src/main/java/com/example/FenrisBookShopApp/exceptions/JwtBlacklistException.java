package com.example.FenrisBookShopApp.exceptions;

import org.springframework.security.oauth2.jwt.JwtException;

public class JwtBlacklistException extends JwtException {
    public JwtBlacklistException(String message) {
        super(message);
    }

    public JwtBlacklistException(String message, Throwable cause) {
        super(message, cause);
    }
}
