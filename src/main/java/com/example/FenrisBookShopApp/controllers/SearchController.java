package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.dto.SearchQueryDto;
import com.example.FenrisBookShopApp.dto.book.BooksPageDto;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.exceptions.EmptySearchException;
import com.example.FenrisBookShopApp.services.book.BookService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SearchController {
    private final BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("searchQueryDto")
    public SearchQueryDto searchQueryDto(@PathVariable(value = "query", required = false) SearchQueryDto query) {
        return query;
    }

    @ModelAttribute("searchResults")
    public Page<BookEntity> searchResults(@PathVariable(value = "query", required = false) SearchQueryDto query) {
        if (query == null) {
            return Page.empty();
        }

        return bookService.getPageOfSearchResultBooks(query.query(), 0, 20);
    }

    @GetMapping(value = {"search", "search/{query}"}, name = "app.search")
    public String search(@PathVariable(required = false) String query) throws EmptySearchException {
        if (query == null) {
            throw new EmptySearchException();
        }

        return "search/index";
    }

    @ResponseBody
    @GetMapping(value = "search/page/{query}", name = "api.search.page")
    public BooksPageDto getNextPage(@RequestParam("offset") int page,
                                    @RequestParam("limit") int limit,
                                    @PathVariable(value = "query") @NotNull SearchQueryDto searchQueryDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchQueryDto.query(), page, limit)
                .getContent());
    }
}
