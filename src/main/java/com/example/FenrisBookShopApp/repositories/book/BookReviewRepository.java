package com.example.FenrisBookShopApp.repositories.book;

import com.example.FenrisBookShopApp.entities.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {
}
