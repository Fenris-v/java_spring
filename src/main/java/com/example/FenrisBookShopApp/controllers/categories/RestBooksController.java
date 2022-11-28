package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.dto.book.BooksPageDto;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class RestBooksController {
    private final BookService bookService;

    @Autowired
    public RestBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "recommended", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksPageDto getBooksPage(@RequestParam("offset") int page, @RequestParam("limit") int limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(page, limit).getContent());
    }

    @GetMapping(value = "recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksPageDto getRecentBooksPage(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam("offset") int page,
            @RequestParam("limit") int limit
    ) {
        return new BooksPageDto(bookService.getPageOfRecentBooksBetweenDates(page, limit, from, to).getContent());
    }

    @GetMapping(value = "popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") int page, @RequestParam("limit") int limit) {
        return new BooksPageDto(bookService.getPageOfPopularBooks(page, limit).getContent());
    }

    @GetMapping(value = "genre/{genreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksPageDto getBooksPageByGenre(
            @PathVariable Long genreId,
            @RequestParam("offset") int page,
            @RequestParam("limit") int limit
    ) {
        List<BookEntity> books = bookService.getPageByGenreId(page, limit, genreId).getContent();
        return new BooksPageDto(books);
    }

    @GetMapping(value = "tag/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooksPageDto getBooksPageByTag(
            @PathVariable Long tagId,
            @RequestParam("offset") int page,
            @RequestParam("limit") int limit
    ) {
        List<BookEntity> books = bookService.getPageByTagId(page, limit, tagId).getContent();
        return new BooksPageDto(books);
    }
}
