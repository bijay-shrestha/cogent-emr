package com.cogent.edgeserver.filters;

import com.cogent.genericservice.security.JwtConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AddRequestHeaderFilter extends ZuulFilter {

    private final JwtConfig jwtConfig;

    public AddRequestHeaderFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        String username = (String) ctx.getRequest().getAttribute("username");


        ctx.addZuulRequestHeader("username", username);

        return null;
    }

    @Override
    public String filterType() {

        return "pre";
    }

    @Override
    public int filterOrder() {

        return 0;
    }


}