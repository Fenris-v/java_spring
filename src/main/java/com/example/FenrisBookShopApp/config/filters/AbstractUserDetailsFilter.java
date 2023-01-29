package com.example.FenrisBookShopApp.config.filters;

import com.example.FenrisBookShopApp.services.security.BookStoreUserDetails;
import com.example.FenrisBookShopApp.services.security.BookStoreUserDetailsService;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.filter.OncePerRequestFilter;

abstract class AbstractUserDetailsFilter extends OncePerRequestFilter {
    protected final JwtUtils jwtUtils;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;

    public AbstractUserDetailsFilter(JwtUtils jwtUtils, BookStoreUserDetailsService bookStoreUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.bookStoreUserDetailsService = bookStoreUserDetailsService;
    }

    protected BookStoreUserDetails getUserDetails(String token) throws ExpiredJwtException {
        if (token == null || jwtUtils.isTokenExpired(token)) {
            return null;
        }

        String username = jwtUtils.extractUsername(token);
        if (username == null) {
            return null;
        }

        return (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(username);
    }
}
