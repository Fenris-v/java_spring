package com.example.FenrisBookShopApp.repositories.book;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookRepositoryTests {
    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBooksByAuthorId() {
        Long authorId = 1L;
        Page<BookEntity> books = bookRepository.findBooksByAuthorId(authorId, PageRequest.of(0, 20));
        assertNotNull(books);
        assertFalse(books.isEmpty());

        for (BookEntity book : books) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.authorsFullName()).contains("Trahear");
        }
    }

    @Test
    void findBookByTitleContainingIgnoreCaseOrderByTitle() {
        String token = "animal";
        Page<BookEntity> books = bookRepository
                .findBookByTitleContainingIgnoreCaseOrderByTitle(token, PageRequest.of(0, 20));

        assertNotNull(books);
        assertFalse(books.isEmpty());

        for (BookEntity book : books) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle()).containsIgnoringCase(token);
        }
    }
}
