package com.cogent.genericservice.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.cogent.genericservice.cookies.CookieConstants.*;

public class CookieUtils {

    public static Cookie createCookie(HttpServletResponse httpServletResponse,
                                      String name,
                                      String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath(defaultPath);
        cookie.setComment(comment);
        cookie.setDomain(domain);

        //cookie.setSecure(true);
        //cookie.setDomain(domain);

        Cookie cookie1 = new Cookie(name, value);
        cookie1.setHttpOnly(true);
        cookie1.setMaxAge(maxAge);
        cookie1.setPath(defaultPath);
        cookie1.setComment(comment);

        httpServletResponse.addCookie(cookie);

        httpServletResponse.addCookie(cookie1);


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
