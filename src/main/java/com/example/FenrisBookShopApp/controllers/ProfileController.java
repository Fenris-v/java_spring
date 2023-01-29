package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import com.example.FenrisBookShopApp.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final AuthenticationService authenticationService;
    private final BookService bookService;

    @GetMapping(value = "my", name = "app.my")
    public String myBooks(@NotNull Model model) {
        List<BookEntity> books = bookService.findBooksByUserId(authenticationService.getCurrentUser().getId());
        model.addAttribute("books", books);
        return "my";
    }

    @GetMapping(value = "profile", name = "app.profile")
    public String profile() {
        return "profile";
    }
}
