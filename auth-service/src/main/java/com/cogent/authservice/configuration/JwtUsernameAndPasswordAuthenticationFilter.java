package com.cogent.authservice.configuration;

import com.cogent.authservice.dto.LoginRequestDTO;
import com.cogent.genericservice.cookies.CookieUtils;
import com.cogent.genericservice.exception.UnauthorisedException;
import com.cogent.genericservice.security.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.cogent.authservice.log.AuthLog.*;
import static com.cogent.authservice.utils.AuthUtils.createToken;
import static com.cogent.authservice.utils.AuthUtils.parseToLoginResponse;
import static com.cogent.genericservice.cookies.CookieConstants.key;
import static com.cogent.genericservice.utils.ObjectToJSONUtils.writeValueAsString;

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

    @Override
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

        String token = createToken(auth, jwtConfig);

        SecurityContextHolder.getContext().setAuthentication(auth);

        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

        Cookie cookie = CookieUtils.createCookie(response, key, token);

        response.getWriter().write(parseToLoginResponse(cookie));

        response.addCookie(cookie);

        response.flushBuffer();

        log.info(AUTHENTICATION_PROCESS_COMPLETED, auth.getName(), token);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException exception) throws IOException {

        log.info(FAILED_AUTHENTICATION);

//        throw new UnauthorisedException(exception.getMessage());
        UnauthorisedException unauthorisedException = new UnauthorisedException(exception.getMessage());

        try {
            String json = writeValueAsString(unauthorisedException.getException());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(json);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//
//        response.getOutputStream()
//                .println(writeValueAsString(unauthorisedException.getException()));
    }

}
