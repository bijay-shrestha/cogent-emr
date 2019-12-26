package com.cogent.edgeserver.checkpoint;

import com.cogent.genericservice.cookies.CookieRequestUtils;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class CookieCheckpoint {

    public static Optional<String> cookieCheckpoint(Cookie[] cookies) {

        Optional<String> token = Optional.empty();
        if (CookieRequestUtils.checkCookies(cookies)) {
            token = CookieRequestUtils.readCookies(cookies);
        }

        System.out.println("Token form the cookies=" + token.get());
        return token;

    }
}
