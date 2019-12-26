package com.cogent.edgeserver.checkpoint;

import com.cogent.edgeserver.exception.InvalidCredentialException;
import com.cogent.genericservice.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

public class JwtTokenCheckpoint {


    public static Claims checkJwtToken(JwtConfig jwtConfig, Optional<String> token) {

        String username = "";
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token.get())
                    .getBody();

            username = claims.getSubject();
            if (claims.getExpiration().before(new Date())) {

                System.out.println("Login Page-----------------------------------------------");
                throw new InvalidCredentialException("Token expired");

            }

            System.out.println(username);

            return claims;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw new InvalidCredentialException("Invalid Credentials");
        }
    }

    public static String checkUserName(JwtConfig jwtConfig, Claims claims) {

        String username = "";
        try {
            username = claims.getSubject();
            if (claims.getExpiration().before(new Date())) {

                System.out.println("Login Page-----------------------------------------------");
                throw new InvalidCredentialException("Token expired");

            }

            System.out.println(username);

            return username;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw new InvalidCredentialException("Invalid Credentials");
        }

    }
}
