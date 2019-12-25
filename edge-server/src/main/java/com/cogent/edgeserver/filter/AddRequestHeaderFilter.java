package com.cogent.edgeserver.filter;

import com.cogent.genericservice.config.JwtConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class AddRequestHeaderFilter extends ZuulFilter {

    private final JwtConfig jwtConfig;

    public AddRequestHeaderFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("ZUUL FILTER ---- ZUUL FILTER ---- ZUUL FILTER");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String username = (String) request.getAttribute("username");

        context.addZuulRequestHeader("username", username);

        return null;

    }
}
