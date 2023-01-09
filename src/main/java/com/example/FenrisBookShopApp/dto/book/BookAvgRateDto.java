package com.example.FenrisBookShopApp.dto.book;

import lombok.Getter;

@Getter
public class BookAvgRateDto {
    private final Long bookId;
    private final Byte rate;

    public BookAvgRateDto(Long bookId, double rate) {
        this.bookId = bookId;
        this.rate = (byte) Math.round(rate);
    }
}
