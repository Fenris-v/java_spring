package com.example.FenrisBookShopApp.controllers.security;

import com.example.FenrisBookShopApp.dto.security.ContactConfirmationResponse;
import com.example.FenrisBookShopApp.dto.security.LoginPayload;
import com.example.FenrisBookShopApp.dto.security.RegistrationForm;
import com.example.FenrisBookShopApp.entities.security.JwtBlacklist;
import com.example.FenrisBookShopApp.helpers.CookieHelper;
import com.example.FenrisBookShopApp.repositories.security.JwtBlacklistRepository;
import com.example.FenrisBookShopApp.services.security.AuthenticationService;
import com.example.FenrisBookShopApp.utils.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZoneId;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtUtils jwtUtils;
    private final JwtBlacklistRepository jwtBlacklistRepository;

    @GetMapping(value = "login", name = "app.login")
    public String auth(@NotNull Model model) {
        model.addAttribute("payload", new LoginPayload());
        return "login";
    }

    @PostMapping("login")
    public String login(LoginPayload payload, @NotNull HttpServletResponse response) {
        ContactConfirmationResponse loginResponse = authenticationService.jwtLogin(payload);
        Cookie cookie = new Cookie(JwtUtils.COOKIE_NAME, loginResponse.getResult());
        response.addCookie(cookie);
        return "redirect:/my";
    }

    @GetMapping(value = "logout", name = "app.logout")
    public String logout(HttpServletRequest request) {
        String token = CookieHelper.getValueFromCookieByName(request, JwtUtils.COOKIE_NAME);
        if (token != null) {
            JwtBlacklist jwtBlacklist = new JwtBlacklist();
            jwtBlacklist.setToken(token);
            Date expiredAt = jwtUtils.extractExpiration(token);
            jwtBlacklist.setExpiredAt(expiredAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            jwtBlacklistRepository.save(jwtBlacklist);
        }

        return "redirect:/";
    }

    @GetMapping(value = "register", name = "app.register")
    public String register(@NotNull Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping(value = "register", name = "app.register.send")
    public String registerSend(RegistrationForm registrationForm, @NotNull Model model) {
        authenticationService.registerNewUser(registrationForm);
        model.addAttribute("registerOk", true);
        return "login";
    }

    @ResponseBody
    @PostMapping(value = "/approveContact", name = "app.contact.approve")
    public ContactConfirmationResponse approveContact() {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @ResponseBody
    @PostMapping(value = "requestContactConfirmation", name = "app.contact.confirmation")
    public ContactConfirmationResponse requestContactConfirmation() {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }
}
