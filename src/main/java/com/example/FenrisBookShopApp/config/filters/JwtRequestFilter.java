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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestFilter extends AbstractUserDetailsFilter {
    @Autowired
    public JwtRequestFilter(JwtUtils jwtUtils, BookStoreUserDetailsService bookStoreUserDetailsService) {
        super(jwtUtils, bookStoreUserDetailsService);
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = CookieHelper.getValueFromCookieByName(request, JwtUtils.COOKIE_NAME);
        BookStoreUserDetails userDetails = getUserDetails(token);
        if (userDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtils.isValidToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
