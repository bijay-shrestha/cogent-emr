package com.cogent.authservice.configuration;

import com.cogent.authservice.LoginRequestDTO;
import com.cogent.genericservice.cookies.CookieUtils;
import com.cogent.genericservice.security.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static com.cogent.authservice.constants.StringConstants.AUTHORITIES;
import static com.cogent.authservice.log.AuthLog.*;
import static com.cogent.authservice.utils.AuthUtils.parseToLoginResponse;
import static com.cogent.genericservice.cookies.CookieConstants.key;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;
    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager,
                                                      JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        log.info(AUTHENTICATION_PROCESS_STARTED);

        try {
            LoginRequestDTO requestDTO = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDTO.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(requestDTO.getUsername(),
                            requestDTO.getPassword(),
                            Collections.emptyList());

            return authManager.authenticate(authToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        log.info(HEADER, jwtConfig.getHeader());
        log.info(PREFIX, jwtConfig.getPrefix());
        log.info(SECRET, jwtConfig.getSecret());
        log.info(EXPIRATION_TIME, jwtConfig.getExpiration());

        String token = createToken(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

        Cookie cookie = CookieUtils.createCookie(response, key, token);

        response.getWriter().write(parseToLoginResponse(cookie));

        response.flushBuffer();

        log.info(AUTHENTICATION_PROCESS_COMPLETED, auth.getName(), token);
    }

    private String createToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(auth.getName())
                .claim(AUTHORITIES, auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    private Date calculateExpirationDate() {
        long now = System.currentTimeMillis();
        return new Date(now + jwtConfig.getExpiration() * 1000);
    }
}
