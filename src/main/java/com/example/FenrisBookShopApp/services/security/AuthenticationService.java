package com.example.FenrisBookShopApp.services.security;

import com.example.FenrisBookShopApp.dto.security.BookStoreUserDetails;
import com.example.FenrisBookShopApp.dto.security.ContactConfirmationResponse;
import com.example.FenrisBookShopApp.dto.security.LoginPayload;
import com.example.FenrisBookShopApp.dto.security.RegistrationForm;
import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.UserRepository;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookStoreUserDetailsService bookStoreUserDetailsService;
    private final JwtUtils jwtUtils;

    public UserEntity registerNewUser(@NotNull RegistrationForm registrationForm) {
        if (UserRepository.findUserByEmail(registrationForm.getEmail()) != null) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setName(registrationForm.getName());
        user.setEmail(registrationForm.getEmail());
        user.setPhone(registrationForm.getPhone());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        UserRepository.save(user);
        return user;
    }

    public ContactConfirmationResponse jwtLogin(@NotNull LoginPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(),
                payload.getPassword()));
        BookStoreUserDetails userDetails = (BookStoreUserDetails) bookStoreUserDetailsService.loadUserByUsername(payload.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(token);
        return response;
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ANONYMOUS"))) {
            System.out.println(123);
            return null;
        }

        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
        return userDetails.user();
    }
}
