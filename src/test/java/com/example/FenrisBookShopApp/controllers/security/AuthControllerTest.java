package com.example.FenrisBookShopApp.controllers.security;

import com.example.FenrisBookShopApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    public AuthControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login").user("test@mail.ru").password("qweqwe"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my"));
    }

    @Test
    void registerTest() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("name", "Test name")
                        .param("phone", "+712312312312")
                        .param("password", "password")
                        .param("email", "test@mail.ru"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("/html/body/div/div[2]/main/form/div/div/div[1]/label/span")
                        .string("Регистрация прошла успешно!"));
    }

    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
