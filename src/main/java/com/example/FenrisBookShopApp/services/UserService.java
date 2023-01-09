package com.example.FenrisBookShopApp.services;

import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.getReferenceById(userId);
    }
}
