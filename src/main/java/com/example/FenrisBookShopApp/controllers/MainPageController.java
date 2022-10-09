package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.data.BookService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public String mainPage(@NotNull Model model) {
        model.addAttribute("bookData", bookService.getBooksData());
        return "index";
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }
}
