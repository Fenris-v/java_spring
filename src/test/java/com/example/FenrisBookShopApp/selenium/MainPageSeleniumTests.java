package com.example.FenrisBookShopApp.selenium;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MainPageSeleniumTests extends AbstractSeleniumTest {
    @Test
    void testMainPageAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
    }

    @Test
    void testMainPageSearchByQuery() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause()
                .setUpSearchToken("animal")
                .pause()
                .submit()
                .pause();

        assertTrue(driver.getPageSource().contains("Animal Farm"));
    }
}
