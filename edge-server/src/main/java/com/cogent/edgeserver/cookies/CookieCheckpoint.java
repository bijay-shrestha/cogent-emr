package com.cogent.edgeserver.cookies;

import com.cogent.genericservice.cookies.CookieRequestUtils;
import com.cogent.genericservice.exception.NoContentFoundException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;

import static com.cogent.edgeserver.constants.ErrorMessageConstants.CookieMessage.COOKIE_KEY_MISMATCHED;
import static com.cogent.edgeserver.constants.ErrorMessageConstants.CookieMessage.COOKIE_NOT_FOUND;
import static com.cogent.edgeserver.log.EdgeServerLog.COOKIE_VALUE;

@Slf4j
public class CookieCheckpoint {

    public static String cookieCheckpoint(Cookie[] cookies) {

        if (!CookieRequestUtils.checkCookies(cookies))
            throw new NoContentFoundException(COOKIE_NOT_FOUND);

       String token = CookieRequestUtils.readCookies(cookies)
               .orElseThrow(()-> new NoContentFoundException(COOKIE_KEY_MISMATCHED));

        log.info(COOKIE_VALUE, token);
        return token;
    }
}
