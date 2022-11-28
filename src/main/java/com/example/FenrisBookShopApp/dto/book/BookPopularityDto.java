package com.example.FenrisBookShopApp.dto.book;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookPopularityDto {
    private BookEntity book;
    private Long buyerCount;
    private Long inCartsCount;
    private Long deferredCount;
}
