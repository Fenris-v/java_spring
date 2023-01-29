package com.example.FenrisBookShopApp.shedules;

import com.example.FenrisBookShopApp.repositories.security.JwtBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtBlacklistCleaner {
    private final JwtBlacklistRepository jwtBlacklistRepository;

    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Moscow")
    public void deleteExpiredTokens() {
        jwtBlacklistRepository.deleteAllByExpiredAtLessThan(LocalDateTime.now());
    }
}
