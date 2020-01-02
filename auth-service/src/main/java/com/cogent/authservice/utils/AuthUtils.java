package com.cogent.authservice.utils;

import com.cogent.authservice.dto.LoginResponse;
import com.cogent.genericservice.utils.ObjectToJSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.Cookie;

/**
 * @author smriti ON 02/01/2020
 */
public class AuthUtils {

    public static String parseToLoginResponse(Cookie cookie) throws JsonProcessingException {
        LoginResponse loginResponse = LoginResponse.builder()
                .cookie(cookie)
                .build();

        return ObjectToJSONUtils.writeObjectToJson(loginResponse);
    }
}
