package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.entities.book.AuthorEntity;
import com.example.FenrisBookShopApp.services.AuthorService;
import com.example.FenrisBookShopApp.services.book.BookService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorsController {
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping(value = "authors", name = "app.authors.list")
    public String authorsPage(@NotNull Model model) {
        model.addAttribute("authorMap", authorService.getAuthorsMap());
        model.addAttribute("isAuthorsPage", true);
        return "authors/index";
    }

    @GetMapping(value = "authors/{slug}", name = "app.authors.show")
    public String author(@PathVariable("slug") String slug, @NotNull Model model) {
        AuthorEntity author = authorService.getAuthorBySlug(slug);
        model.addAttribute("author", author);
        model.addAttribute("books", bookService.findBooksByAuthorId(author.getId()));

        return "authors/slug";
    }
}
