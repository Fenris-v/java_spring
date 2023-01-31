package com.example.FenrisBookShopApp.selenium;

import org.openqa.selenium.chrome.ChromeDriver;

public class BookPage {
    private final String url;
    private final ChromeDriver driver;

    public BookPage(String url, ChromeDriver driver) {
        this.url = url;
        this.driver = driver;
    }

    public BookPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }
}
