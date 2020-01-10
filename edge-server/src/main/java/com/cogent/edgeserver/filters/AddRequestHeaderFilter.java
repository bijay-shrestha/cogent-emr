package com.cogent.edgeserver.filters;

import com.cogent.contextserver.security.JwtConfig;
import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddRequestHeaderFilter extends ZuulFilter {

    @Autowired
    private JwtConfig jwtConfig;

    public AddRequestHeaderFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("**** ==== INSIDE AddRequestHeaderFilter.class (edge-server) ==== ****");
        RequestContext ctx = RequestContext.getCurrentContext();
        String username = (String) ctx.getRequest().getAttribute("username");

        ctx.addZuulRequestHeader("username", username);

        return null;
    }


    @Override
    public int filterOrder() {
        return 0;
    }

}