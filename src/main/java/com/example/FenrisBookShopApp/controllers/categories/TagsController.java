package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.entities.other.TagEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import com.example.FenrisBookShopApp.services.tag.TagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tags")
public class TagsController {
    private final TagService tagService;
    private final BookService bookService;

    @Autowired
    public TagsController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }

    @GetMapping(value = "{tagId}", name = "app.tags.books")
    public String show(@PathVariable Long tagId, @NotNull Model model) {
        TagEntity tag = tagService.findById(tagId);
        model.addAttribute("tag", tag);
        model.addAttribute("books", bookService.findBooksByTagId(tagId));
        return "tags/index";
    }
}
