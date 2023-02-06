package com.example.FenrisBookShopApp.services.security;

import com.example.FenrisBookShopApp.dto.security.ContactConfirmationResponse;
import com.example.FenrisBookShopApp.dto.security.LoginPayload;
import com.example.FenrisBookShopApp.dto.security.RegistrationForm;
import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.UserRepository;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;
    private final JwtUtils jwtUtils;

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    public AuthenticationServiceTest(AuthenticationService authenticationService,
                                     PasswordEncoder passwordEncoder,
                                     JwtUtils jwtUtils) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setEmail("test@mail.ru");
        registrationForm.setName("Test");
        registrationForm.setPassword("qweqwe");
        registrationForm.setPhone("+799978945612");
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void testRegisterNewUser() {
        UserEntity user = authenticationService.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPassword(), user.getPassword()));
        assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
        assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));
        assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));

        Mockito.verify(userRepositoryMock, Mockito.times(1))
                .save(Mockito.any(UserEntity.class));
    }

    @Test
    void testRegisterNewUserFail() {
        Mockito.doReturn(new UserEntity())
                .when(userRepositoryMock)
                .findUserByEmail(registrationForm.getEmail());

        UserEntity user = authenticationService.registerNewUser(registrationForm);
        assertNull(user);
    }

    @Test
    void testJwtLogin() {
        UserEntity user = new UserEntity();
        user.setName(registrationForm.getName());
        user.setEmail(registrationForm.getEmail());
        user.setPhone(registrationForm.getPhone());
        user.setPassword(registrationForm.getPassword());

        Mockito.doReturn(user)
                .when(userRepositoryMock)
                .findUserByEmail(registrationForm.getEmail());

        LoginPayload payload = new LoginPayload();
        payload.setUsername(registrationForm.getEmail());
        payload.setPassword(registrationForm.getPassword());

        ContactConfirmationResponse response = authenticationService.jwtLogin(payload);
        assertNotNull(response);
        assertNotNull(response.getResult());

        String jwtToken = response.getResult();
        assertThat(jwtUtils.extractUsername(jwtToken)).isEqualTo(registrationForm.getEmail());
    }

    @Test
    void testJwtLoginFailed() {
        LoginPayload payload = new LoginPayload();
        payload.setUsername(registrationForm.getEmail());
        payload.setPassword(registrationForm.getPassword());

        ContactConfirmationResponse response = null;
        String errorMessage = null;
        try {
            response = authenticationService.jwtLogin(payload);
        } catch (UsernameNotFoundException e) {
            errorMessage = e.getMessage();
        } finally {
            assertThat(errorMessage).isEqualTo("user not found");
            assertNull(response);
        }
    }
}
