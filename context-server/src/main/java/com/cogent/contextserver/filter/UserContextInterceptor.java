package com.cogent.contextserver.filter;

import com.cogent.genericservice.config.JwtConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {


    private final JwtConfiguration jwtConfig;

    public UserContextInterceptor(JwtConfiguration jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(jwtConfig.getHeader(), UserContextHolder.getContext().getUsername());


        return execution.execute(request, body);
    }
}