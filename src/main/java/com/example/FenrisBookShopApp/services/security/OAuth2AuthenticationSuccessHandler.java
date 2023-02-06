package com.example.FenrisBookShopApp.services.security;

import com.example.FenrisBookShopApp.dto.security.BookStoreUserDetails;
import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.UserRepository;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        @NotNull HttpServletResponse response,
                                        @NotNull Authentication authentication) throws IOException {
        UserEntity user = getUser((DefaultOAuth2User) authentication.getPrincipal());
        BookStoreUserDetails userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtils.generateToken(userDetails);
        Cookie cookie = new Cookie(JwtUtils.COOKIE_NAME, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, "/my");
    }

    @NotNull
    private UserEntity getUser(@NotNull DefaultOAuth2User principal) {
        UserEntity user = userRepository.findUserByEmail(principal.getAttribute("email"));
        if (user != null) {
            return user;
        }

        user = new UserEntity();
        user.setName(principal.getAttribute("name"));
        user.setEmail(principal.getAttribute("email"));
        userRepository.save(user);
        return user;
    }
}
