package com.example.FenrisBookShopApp.config;

import com.example.FenrisBookShopApp.config.filters.FilterExceptionHandler;
import com.example.FenrisBookShopApp.config.filters.JwtBlacklistFilter;
import com.example.FenrisBookShopApp.config.filters.JwtRequestFilter;
import com.example.FenrisBookShopApp.services.security.CustomLogoutHandler;
import com.example.FenrisBookShopApp.services.security.OAuth2AuthenticationSuccessHandler;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtBlacklistFilter jwtBlacklistFilter;
    private final FilterExceptionHandler filterExceptionHandler;
    private final CustomLogoutHandler logoutHandler;
    private final CustomAuthenticationProvider authenticationProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/my**",
                "/profile**").authenticated().requestMatchers("/assets/**").permitAll().anyRequest().permitAll();

        http.authenticationProvider(authenticationProvider).formLogin().loginPage("/login").loginProcessingUrl("/");

        http.oauth2Login().loginPage("/login").successHandler(oAuth2AuthenticationSuccessHandler).and().oauth2Client();

        http.logout().addLogoutHandler(logoutHandler).logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies(JwtUtils.COOKIE_NAME).invalidateHttpSession(
                true).permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtBlacklistFilter, JwtRequestFilter.class);
        http.addFilterBefore(filterExceptionHandler, JwtBlacklistFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(@NotNull HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).authenticationProvider(authenticationProvider).build();
    }
}
