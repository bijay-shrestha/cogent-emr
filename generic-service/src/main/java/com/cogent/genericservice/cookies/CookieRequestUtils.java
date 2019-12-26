package com.cogent.genericservice.cookies;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

import static com.cogent.genericservice.cookies.CookieConstants.key;

public class CookieRequestUtils {

    public static boolean checkCookies(Cookie[] cookies) {
        return cookies.length != 0;
    }

    public static Optional<String> readCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
