package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.dto.book.ReviewDto;
import com.example.FenrisBookShopApp.entities.book.review.BookReviewEntity;
import com.example.FenrisBookShopApp.services.UserService;
import com.example.FenrisBookShopApp.services.book.BookReviewService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReviewController {
    private final UserService userService;
    private final BookReviewService bookReviewService;

    @Autowired
    public ReviewController(UserService userService, BookReviewService bookReviewService) {
        this.userService = userService;
        this.bookReviewService = bookReviewService;
    }

    // todo: изменить юзер_ид, когда будет авторизация
    @PostMapping("bookReview")
    public Map<String, ?> addReview(@Valid @NotNull ReviewDto reviewDto) {
        BookReviewEntity bookReview = new BookReviewEntity();
        bookReview.setBookId(reviewDto.getBookId());
        bookReview.setText(reviewDto.getText());
        bookReview.setUser(userService.findUserById(1L));
        bookReviewService.saveEntity(bookReview);

        Map<String, Object> response = new HashMap<>();
        response.put("result", true);
        return response;
    }
}
