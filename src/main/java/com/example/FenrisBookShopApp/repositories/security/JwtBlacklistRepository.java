package com.example.FenrisBookShopApp.repositories.security;

import com.example.FenrisBookShopApp.entities.security.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    boolean existsByToken(String token);

    void deleteAllByExpiredAtLessThan(LocalDateTime now);
}
