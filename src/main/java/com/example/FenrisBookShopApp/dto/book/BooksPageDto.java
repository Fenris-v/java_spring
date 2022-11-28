package com.example.FenrisBookShopApp.dto.book;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class BooksPageDto {
    private final Integer count;
    private final List<BookEntity> books;

    public BooksPageDto(@NotNull List<BookEntity> books) {
        this.books = books;
        count = books.size();
    }
}
