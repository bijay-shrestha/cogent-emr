package com.cogent.adminservice.security;

import com.cogent.contextserver.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class UserCredentials {

    private static JwtConfig jwtConfig;

    public UserCredentials(JwtConfig jwtConfig) {
        UserCredentials.jwtConfig = jwtConfig;
    }

    public static  String userData(String header) {

        /*   new token getting code*/
        String token = header.replace(jwtConfig.getPrefix(), "");


        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            System.out.println(username);

            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
