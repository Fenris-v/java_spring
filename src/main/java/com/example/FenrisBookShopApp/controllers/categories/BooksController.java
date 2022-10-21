package com.example.FenrisBookShopApp.controllers.categories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BooksController {
    @GetMapping(value = "recent", name = "app.new_book.list")
    public String recent() {
        return "books/recent";
    }

    @GetMapping(value = "/popular", name = "app.popular.list")
    public String popular() {
        return "books/popular";
    }
}
