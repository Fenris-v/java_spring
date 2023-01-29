package com.example.FenrisBookShopApp.services.security;

import com.example.FenrisBookShopApp.entities.security.JwtBlacklist;
import com.example.FenrisBookShopApp.helpers.CookieHelper;
import com.example.FenrisBookShopApp.repositories.security.JwtBlacklistRepository;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = CookieHelper.getValueFromCookieByName(request, JwtUtils.COOKIE_NAME);
        if (token != null) {
            addJwtToBlacklist(token);
        }
    }

    private void addJwtToBlacklist(String token) {
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(token);
        Date expiredAt = jwtUtils.extractExpiration(token);
        jwtBlacklist.setExpiredAt(expiredAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        jwtBlacklistRepository.save(jwtBlacklist);
    }
}
