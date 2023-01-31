package com.example.FenrisBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {
    private static final String URL = "http://localhost:8085/";
    private final ChromeDriver driver;

    public MainPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public MainPage callPage() {
        driver.get(URL);
        return this;
    }

    public MainPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MainPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));
        element.sendKeys(token);
        return this;
    }

    public MainPage clickBook() {
        WebElement element = driver.findElement(By.className("Card-picture"));
        element.click();
        return this;
    }

    public MainPage clickLogo() {
        WebElement element = driver.findElement(By.className("logo"));
        element.click();
        return this;
    }

    public MainPage submit() {
        WebElement element = driver.findElement(By.id("search"));
        element.submit();
        return this;
    }

    public MainPage clickHeaderMenu(int index) {
        WebElement element = driver.findElements(By.className("menu-link")).get(index);
        element.click();
        return this;
    }

    public MainPage clickElementByCssSelector(String selector) {
        WebElement element = driver.findElement(By.cssSelector(selector));
        element.click();
        return this;
    }
}
