package com.example.FenrisBookShopApp.controllers.categories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenresController {
    @GetMapping(value = "/genres", name = "app.genres.list")
    public String genresPage() {
        return "genres/index";
    }
}
