package com.example.FenrisBookShopApp.repositories;

import com.example.FenrisBookShopApp.entities.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testAddNewUser() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.test");
        user.setPassword("123123");
        user.setName("TESTER");

        assertNotNull(userRepository.save(user));

        userRepository.delete(user);
    }
}
