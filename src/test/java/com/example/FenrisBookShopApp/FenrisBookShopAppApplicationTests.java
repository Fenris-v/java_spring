package com.example.FenrisBookShopApp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FenrisBookShopAppApplicationTests {
    @Value("${auth.secret}")
    private String authSecret;

    private final FenrisBookShopAppApplication application;

    @Autowired
    public FenrisBookShopAppApplicationTests(FenrisBookShopAppApplication application) {
        this.application = application;
    }

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

    @Test
    void verifyAuthSecret() {
        assertThat(authSecret, Matchers.containsString("fenris"));
    }
}
