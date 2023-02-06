package com.example.FenrisBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

abstract class AbstractSeleniumTest {
    protected static ChromeDriver driver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "/home/fenris/chromedriver_linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
