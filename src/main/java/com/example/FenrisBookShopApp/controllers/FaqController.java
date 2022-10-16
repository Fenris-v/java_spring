package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {
    @GetMapping(value = "/faq", name = "app.faq")
    public String genresPage() {
        return "faq";
    }
}
