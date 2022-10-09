package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.data.AuthorService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public String authorsPage(@NotNull Model model) {
        model.addAttribute("authorData", authorService.getAuthorsData());
        return "authors/index";
    }
}
