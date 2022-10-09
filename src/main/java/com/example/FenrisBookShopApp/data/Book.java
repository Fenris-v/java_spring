package com.example.FenrisBookShopApp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book {
    private Integer id;
    private String author;
    private String title;
    private String priceOld;
    private String price;
}
