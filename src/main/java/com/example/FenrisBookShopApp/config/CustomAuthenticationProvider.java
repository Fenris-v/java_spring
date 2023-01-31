package com.example.FenrisBookShopApp.config;

import com.example.FenrisBookShopApp.dto.security.BookStoreUserDetails;
import com.example.FenrisBookShopApp.dto.security.ContactConfirmationResponse;
import com.example.FenrisBookShopApp.services.security.BookStoreUserDetailsService;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;

    @Override
    public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        BookStoreUserDetails userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(
                authentication.getName());
        String token = jwtUtils.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(token);
        return authentication;
    }

    @Override
    public boolean supports(@NotNull Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
