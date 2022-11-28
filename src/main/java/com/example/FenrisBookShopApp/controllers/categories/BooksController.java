package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.services.book.BookService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "recent", name = "app.new_book.list", produces = MediaType.TEXT_HTML_VALUE)
    public String recent(@NotNull Model model) {
        model.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 20));
        return "books/recent";
    }

    @GetMapping(value = "popular", name = "app.popular.list", produces = MediaType.TEXT_HTML_VALUE)
    public String popular(@NotNull Model model) {
        model.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 20));
        return "books/popular";
    }
}
