package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.annotations.Loggable;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.entities.other.TagEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import com.example.FenrisBookShopApp.services.tag.TagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController {
    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<BookEntity> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 20).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<BookEntity> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 20).getContent();
    }

    @ModelAttribute("tags")
    public List<TagEntity> tags() {
        return tagService.findAll();
    }

    @ModelAttribute("popular")
    public List<BookEntity> popular() {
        return bookService.getPageOfPopularBooks(0, 20).getContent();
    }

    @GetMapping(value = "/", name = "app.main")
    @Loggable
    public String mainPage(@NotNull Model model) {
        model.addAttribute("isMainPage", true);
        return "index";
    }
}
