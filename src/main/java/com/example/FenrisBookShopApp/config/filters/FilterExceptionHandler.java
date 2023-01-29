package com.example.FenrisBookShopApp.config.filters;

import com.example.FenrisBookShopApp.exceptions.JwtBlacklistException;
import com.example.FenrisBookShopApp.helpers.CookieHelper;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UsernameNotFoundException | JwtBlacklistException ex) {
            handleInvalidTokenException(request, response);
        }
    }

    private void handleInvalidTokenException(@NotNull HttpServletRequest request,
                                             @NotNull HttpServletResponse response) {
        CookieHelper.removeCookieByName(request, response, JwtUtils.COOKIE_NAME);
        response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        response.setHeader("Location", "/");
    }
}
