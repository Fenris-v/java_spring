package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping(value = "/cart", name = "app.cart")
    public String genresPage() {
        return "cart";
    }
}
