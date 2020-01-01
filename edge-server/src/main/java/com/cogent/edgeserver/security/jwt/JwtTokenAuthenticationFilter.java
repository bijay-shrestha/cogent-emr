package com.cogent.edgeserver.security.jwt;

import com.cogent.genericservice.cookies.CookieUtils;
import com.cogent.genericservice.security.JwtConfig;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cogent.edgeserver.constants.TokenConstants.AUTHORITIES;
import static com.cogent.edgeserver.constants.TokenConstants.USERNAME;
import static com.cogent.edgeserver.cookies.CookieCheckpoint.cookieCheckpoint;
import static com.cogent.edgeserver.security.jwt.JwtTokenProvider.fetchUsernameFromClaims;
import static com.cogent.edgeserver.security.jwt.JwtTokenProvider.validateJwtToken;
import static com.cogent.genericservice.cookies.CookieConstants.key;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            String header = request.getHeader(jwtConfig.getHeader());
            if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
                chain.doFilter(request, response);
                return;
            }
        }

        if (request.getRequestURI().equals("/cogent/logout")) {
            CookieUtils.clear(response, key);
            return;
        }

        authorization(request, response, cookies, chain);
    }

    public void authorization(HttpServletRequest request,
                              HttpServletResponse response,
                              Cookie[] cookies,
                              FilterChain chain) throws IOException, ServletException {

        String token = cookieCheckpoint(cookies);
        Claims claims = validateJwtToken(jwtConfig, token);
        String username = fetchUsernameFromClaims(claims);

        if (!Objects.isNull(username)) {
            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) claims.get(AUTHORITIES);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null,
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            request.setAttribute(USERNAME, username);

            chain.doFilter(request, response);
        }
    }
}


