package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.helpers.strings.StringHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@AllArgsConstructor
abstract class AbstractUserCookieService {
    protected final static char COOKIE_SEPARATOR = '|';
    private final static String PATH = "/";
    private final static int TTL = 60 * 60 * 24 * 30;

    protected List<Long> getIdsFromCookieString(String data) {
        data = StringHelper.trimChar(data, COOKIE_SEPARATOR);
        return Arrays.stream(data.split("\\" + COOKIE_SEPARATOR)).map(Long::valueOf).toList();
    }

    protected String addToCookieString(String data, String bookId) {
        data = StringHelper.trimChar(data, COOKIE_SEPARATOR);
        String[] cookieIds = data.split("\\" + COOKIE_SEPARATOR);

        if (Arrays.asList(cookieIds).contains(bookId)) {
            return data;
        }

        StringJoiner stringJoiner = new StringJoiner(String.valueOf(COOKIE_SEPARATOR));
        stringJoiner.add(data).add(bookId);
        return stringJoiner.toString();
    }

    protected String removeFromCookieString(String data, @NotNull String bookId) {
        data = StringHelper.trimChar(data, COOKIE_SEPARATOR);
        List<String> cookieIds = new ArrayList<>(Arrays.asList(data.split("\\" + COOKIE_SEPARATOR)));

        if (cookieIds.stream().noneMatch(bookId::equals)) {
            return data;
        }

        cookieIds.remove(bookId);
        return String.join(String.valueOf(COOKIE_SEPARATOR), cookieIds);
    }

    protected void setCookie(@NotNull HttpServletResponse response, String data, String name) {
        Cookie cookie = new Cookie(name, data);
        cookie.setPath(PATH);
        cookie.setMaxAge(TTL);
        response.addCookie(cookie);
    }
}
