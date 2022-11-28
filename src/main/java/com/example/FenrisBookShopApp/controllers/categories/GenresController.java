package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.entities.genre.GenreEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import com.example.FenrisBookShopApp.services.genre.GenreService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("genres")
public class GenresController {
    private final GenreService genreService;
    private final BookService bookService;

    @Autowired
    public GenresController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping(value = "", name = "app.genres.list")
    public String genresPage(@NotNull Model model) {
        model.addAttribute("genres", genreService.getGenres());
        return "genres/index";
    }

    @GetMapping(value = "{slug}", name = "app.genres.show")
    public String genresPage(@PathVariable String slug, @NotNull Model model) {
        GenreEntity genre = genreService.getGenreBySlug(slug);
        model.addAttribute("genre", genre);
        model.addAttribute("books", bookService.getPageByGenreId(0, 20, genre.getId()));
        return "genres/slug";
    }
}
