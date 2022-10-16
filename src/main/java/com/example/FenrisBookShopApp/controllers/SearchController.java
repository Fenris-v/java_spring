package com.example.FenrisBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    @GetMapping(value = "/search", name = "app.search")
    public String genresPage() {
        return "search/index";
    }
}
