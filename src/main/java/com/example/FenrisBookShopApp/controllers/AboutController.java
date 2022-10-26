package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping(value = "/about", name = "app.about")
    public String genresPage() {
        return "about";
    }
}
