package com.example.FenrisBookShopApp.selenium;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NavigateSeleniumTest extends AbstractSeleniumTest {
    @Test
    void testNavigate() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);

        mainPage.callPage().pause().clickBook().pause();
        sourceContains(driver,
                "<button class=\"btn btn_primary btn_outline\" data-sendstatus=\"KEPT\" data-alttext=\"Отложена\" data-btntype=\"check\" data-btnradio=\"buyblock\" data-check=\"false\"");

        mainPage.clickLogo().pause();
        isMainPage(driver);

        mainPage.clickHeaderMenu(1).pause();
        sourceContains(driver, "Жанры");

        mainPage.clickElementByCssSelector(".Tag a").pause();
        sourceContains(driver, "<div class=\"Cards Cards_refresh\">");

        mainPage.clickHeaderMenu(2).pause();
        sourceContains(driver, "id=\"fromdaterecent\"");

        mainPage.clickHeaderMenu(3).pause();
        sourceContains(driver, "Популярное");

        mainPage.clickElementByCssSelector(".Card-picture").pause();
        sourceContains(driver,
                "<button class=\"btn btn_primary btn_outline\" data-sendstatus=\"KEPT\" data-alttext=\"Отложена\" data-btntype=\"check\" data-btnradio=\"buyblock\" data-check=\"false\"");

        mainPage.clickHeaderMenu(4).pause();
        sourceContains(driver, "<div class=\"Authors-block\">");

        mainPage.clickElementByCssSelector(".Authors-item a").pause();
        sourceContains(driver, "Биография");
    }

    private void isMainPage(@NotNull ChromeDriver driver) {
        sourceContains(driver,
                "<div class=\"Slider-box Cards slick-initialized slick-slider\" data-load=\"recommended\" data-loadoffset=\"0\" data-loadlimit=\"20\">");
    }

    private void sourceContains(@NotNull ChromeDriver driver, String s) {
        assertTrue(driver.getPageSource().contains(s));
    }
}
