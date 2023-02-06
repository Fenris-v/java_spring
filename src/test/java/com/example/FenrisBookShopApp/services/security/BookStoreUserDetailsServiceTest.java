package com.example.FenrisBookShopApp.services.security;

import com.example.FenrisBookShopApp.dto.security.BookStoreUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookStoreUserDetailsServiceTest {
    private final BookStoreUserDetailsService bookStoreUserDetailsService;

    @Autowired
    public BookStoreUserDetailsServiceTest(BookStoreUserDetailsService bookStoreUserDetailsService) {
        this.bookStoreUserDetailsService = bookStoreUserDetailsService;
    }

    @Test
    void testLoadUserByUsername() {
        BookStoreUserDetails userDetails = (BookStoreUserDetails) bookStoreUserDetailsService
                .loadUserByUsername("atordiffe1@blog.com");

        assertNotNull(userDetails);
        assertThat(userDetails.getUsername()).isEqualTo("atordiffe1@blog.com");
    }
}
