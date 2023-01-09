package com.example.FenrisBookShopApp.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateGroupedCountDto {
    private Long count;
    private Byte value;
}
