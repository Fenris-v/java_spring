package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {
    @GetMapping(value = "/contacts", name = "app.contacts")
    public String genresPage() {
        return "contacts";
    }
}
