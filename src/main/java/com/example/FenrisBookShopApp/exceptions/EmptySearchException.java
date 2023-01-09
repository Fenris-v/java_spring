package com.example.FenrisBookShopApp.exceptions;

public class EmptySearchException extends Exception {
    public EmptySearchException() {
        super("Пустой поисковый запрос");
    }
}
