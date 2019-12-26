package com.cogent.genericservice.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.cogent.genericservice.cookies.CookieConstants.*;
import static com.cogent.genericservice.cookies.CookieConstants.defaultPath;

public class CookieUtils {

    public static Cookie createCookie(
            HttpServletResponse httpServletResponse,
            String name,
            String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath(defaultPath);
        cookie.setComment(comment);

        //cookie.setSecure(true);
        //cookie.setDomain(domain);
        //cookie.setVersion(0);

        httpServletResponse.addCookie(cookie);
        return cookie;
    }

    public static void clear(HttpServletResponse httpServletResponse, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(defaultPath);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

    }
}
