package com.example.FenrisBookShopApp.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateDto {
    @NotNull(message = "Value can't be null")
    @Min(value = 1, message = "Value must be greater than 0")
    private Long bookId;

    @NotNull(message = "Value can't be null")
    @Min(value = 1, message = "Value must be greater than 0")
    @Max(value = 5, message = "Value must be less than 6")
    private Byte value;
}
