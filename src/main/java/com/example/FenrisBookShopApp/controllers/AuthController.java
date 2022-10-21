package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping(value = "signin", name = "app.sign_in")
    public String genresPage() {
        return "signin";
    }
}
