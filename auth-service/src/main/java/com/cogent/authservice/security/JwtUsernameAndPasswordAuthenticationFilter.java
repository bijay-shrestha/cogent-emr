package com.cogent.authservice.security;
import com.cogent.authservice.dto.LoginResponse;
import com.cogent.authservice.model.JwtRequest;

import com.cogent.contextserver.security.JwtConfig;
import com.cogent.genericservice.cookies.CookieUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static com.cogent.genericservice.cookies.CookieConstants.key;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;
    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager,
                                                      JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        try {
            JwtRequest creds = new ObjectMapper().readValue(request.getInputStream(),
                    JwtRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList());
            Authentication  authObj = authManager.authenticate(authToken);
            log.info("CONGRATULATIONS, USERNAME AND PASSWORD IS A MATCH ====" +
                    " AUTHENTICATION SUCCESSFUL ++++ (auth-service)");
            return authObj;
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

        log.info("Granted Authorities {} "+ auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities",
                        auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())).setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();


        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info(":::: ====== ++++++ {} SUCCESSFULLY AUTHENTICATED from JwtUsernameAndPasswordAuthenticationFilter.class" +
                " ++++++ ====== ::::", auth.getName());

        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

        Cookie c = CookieUtils.createCookie(response, key, token);

        LoginResponse loginResponse = LoginResponse.builder().
                cookie(c)
                .build();

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(loginResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.getWriter().write(json);
        response.flushBuffer();
    }


}
