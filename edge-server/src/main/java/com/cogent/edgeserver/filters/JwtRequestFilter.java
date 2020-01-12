package com.cogent.edgeserver.filters;

import com.cogent.contextserver.security.JwtConfig;
import com.cogent.edgeserver.checkpoint.CookieCheckpoint;
import com.cogent.edgeserver.checkpoint.JwtTokenCheckpoint;
import com.cogent.genericservice.cookies.CookieUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cogent.genericservice.cookies.CookieConstants.key;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtRequestFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        log.info(" :::::: ===== JwtRequestFilter.class (edge-server) ====== ::::::");
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


        Optional<String> token = CookieCheckpoint.cookieCheckpoint(cookies);

        Claims claims = JwtTokenCheckpoint.checkJwtToken(jwtConfig, token);
        String username = JwtTokenCheckpoint.checkUsername(jwtConfig, claims);

        if (username != null) {
            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) claims.get("authorities");
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null, authorities.stream().map(
                            SimpleGrantedAuthority::new
                    ).collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info(String.format("%s REQUEST TO %s", request.getMethod(), request.getRequestURL().
                    toString()) + " " + request.getHeader(jwtConfig.getHeader()));


            Object principal =
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            log.info("NAME ((^.^)) {}", username);

            request.setAttribute("AUTHENTICATED_USER", username);
            chain.doFilter(request, response);
        }
    }
}


