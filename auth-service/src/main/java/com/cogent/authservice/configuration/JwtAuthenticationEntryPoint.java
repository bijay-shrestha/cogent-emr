package com.cogent.authservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author smriti ON 02/01/2020
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("Jwt authentication failed FROM AUTH:" + authException);

//        throw new DataDuplicationException("Inavalid credentials");

        response.sendError(HttpServletResponse.SC_OK	, "Jwt authentication failed");
    }
}
