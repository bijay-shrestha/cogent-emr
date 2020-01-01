package com.cogent.edgeserver.security.jwt;

import com.cogent.edgeserver.exception.InvalidCredentialException;
import com.cogent.genericservice.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

import static com.cogent.genericservice.constants.ErrorMessageConstants.TokenMessages.INVALID_CREDENTIALS;
import static com.cogent.genericservice.constants.ErrorMessageConstants.TokenMessages.TOKEN_EXPIRED;

@Slf4j
public class JwtTokenProvider {

    public static Claims validateJwtToken(JwtConfig jwtConfig, String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date()))
                throw new InvalidCredentialException(TOKEN_EXPIRED);

            return claims;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw new InvalidCredentialException(INVALID_CREDENTIALS);
        }
    }

    public static String fetchUsernameFromClaims(Claims claims) {
        return claims.getSubject();
    }
}
