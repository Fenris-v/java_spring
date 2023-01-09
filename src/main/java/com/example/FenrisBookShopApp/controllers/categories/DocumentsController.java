package com.example.FenrisBookShopApp.controllers.categories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController {
    @GetMapping(value = "documents", name = "app.documents.list")
    public String genresPage() {
        return "documents/index";
    }
}
