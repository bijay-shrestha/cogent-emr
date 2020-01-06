package com.cogent.authservice.utils;

import com.cogent.authservice.dto.LoginResponse;
import com.cogent.genericservice.security.JwtConfig;
import com.cogent.genericservice.utils.ObjectToJSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.stream.Collectors;

import static com.cogent.authservice.constants.TokenConstants.AUTHORITIES;

/**
 * @author smriti ON 02/01/2020
 */
public class AuthUtils {

    public static String createToken(Authentication auth, JwtConfig jwtConfig) {
        return Jwts.builder()
                .setSubject(auth.getName())
                .claim(AUTHORITIES, auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(jwtConfig))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    private static Date calculateExpirationDate(JwtConfig jwtConfig) {
        long now = System.currentTimeMillis();
        return new Date(now + jwtConfig.getExpiration() * 1000);
    }

    public static String parseToLoginResponse(Cookie cookie) throws JsonProcessingException {
        LoginResponse loginResponse = LoginResponse.builder()
                .cookie(cookie)
                .build();

        return ObjectToJSONUtils.writeValueAsString(loginResponse);
    }
}
