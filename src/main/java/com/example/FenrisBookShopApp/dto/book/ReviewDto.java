package com.example.FenrisBookShopApp.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDto {
    @NotNull
    @Min(0)
    private Long bookId;

    @NotNull
    @NotBlank
    private String text;
}
