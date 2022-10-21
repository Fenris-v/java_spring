package com.example.FenrisBookShopApp.controllers.categories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostponedController {
    @GetMapping(value = "postponed", name = "app.favorite.list")
    public String genresPage() {
        return "postponed";
    }
}
