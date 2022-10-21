package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.data.Author;
import com.example.FenrisBookShopApp.data.AuthorService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.TreeMap;
import java.util.TreeSet;

@Controller
public class AuthorsController {
    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorMap")
    public TreeMap<Character, TreeSet<Author>> authorsMap() {
        return authorService.getAuthorsMap();
    }

    @GetMapping(value = "authors", name = "app.authors.list")
    public String authorsPage(@NotNull Model model) {
        model.addAttribute("authorData", authorService.getAuthorsMap());
        return "authors/index";
    }
}
