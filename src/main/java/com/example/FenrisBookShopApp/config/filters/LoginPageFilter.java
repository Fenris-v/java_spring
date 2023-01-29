package com.example.FenrisBookShopApp.config.filters;

import com.example.FenrisBookShopApp.helpers.CookieHelper;
import com.example.FenrisBookShopApp.services.security.BookStoreUserDetails;
import com.example.FenrisBookShopApp.services.security.BookStoreUserDetailsService;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginPageFilter extends AbstractUserDetailsFilter {
    private static final List<String> REDIRECT_URLS = new ArrayList<>(Arrays.asList("/login", "/register"));

    @Autowired
    public LoginPageFilter(JwtUtils jwtUtils, BookStoreUserDetailsService bookStoreUserDetailsService) {
        super(jwtUtils, bookStoreUserDetailsService);
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (!REDIRECT_URLS.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = CookieHelper.getValueFromCookieByName(request, JwtUtils.COOKIE_NAME);
        BookStoreUserDetails userDetails = getUserDetails(token);
        if (userDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtils.isValidToken(token, userDetails)) {
            response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
            response.setHeader("Location", getRedirectUrl(request));
        }

        filterChain.doFilter(request, response);
    }

    private String getRedirectUrl(@NotNull HttpServletRequest request) {
        return request.getHeader(HttpHeaders.REFERER) == null
                || REDIRECT_URLS.contains(request.getHeader(HttpHeaders.REFERER))
                ? "/" : request.getHeader(HttpHeaders.REFERER);
    }
}
