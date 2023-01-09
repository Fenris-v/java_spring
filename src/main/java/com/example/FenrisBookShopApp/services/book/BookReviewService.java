package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.entities.book.review.BookReviewEntity;
import com.example.FenrisBookShopApp.repositories.book.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;

    @Autowired
    public BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public void saveEntity(BookReviewEntity bookReview) {
        bookReviewRepository.save(bookReview);
    }
}
