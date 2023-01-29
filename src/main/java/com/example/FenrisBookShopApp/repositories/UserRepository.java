package com.example.FenrisBookShopApp.repositories;

import com.example.FenrisBookShopApp.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);
}
