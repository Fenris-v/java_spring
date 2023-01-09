package com.example.FenrisBookShopApp.helpers.strings;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {
    public static String trimChar(String str, char character) {
        String charInString = String.valueOf(character);
        str = StringUtils.stripStart(str, charInString);
        str = StringUtils.stripEnd(str, charInString);
        return str;
    }
}
