package com.example.FenrisBookShopApp.config.filters;

import com.example.FenrisBookShopApp.exceptions.JwtBlacklistException;
import com.example.FenrisBookShopApp.helpers.CookieHelper;
import com.example.FenrisBookShopApp.repositories.security.JwtBlacklistRepository;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtBlacklistFilter extends OncePerRequestFilter {
    private final JwtBlacklistRepository jwtBlacklistRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = CookieHelper.getValueFromCookieByName(request, JwtUtils.COOKIE_NAME);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtBlacklistRepository.existsByToken(token)) {
            throw new JwtBlacklistException("Token in blacklist");
        }

        filterChain.doFilter(request, response);
    }
}
