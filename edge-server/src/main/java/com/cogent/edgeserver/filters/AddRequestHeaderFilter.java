package com.cogent.edgeserver.filters;

import com.cogent.contextserver.security.JwtConfig;
import com.cogent.edgeserver.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddRequestHeaderFilter extends ZuulFilter {
    private static final int      FILTER_ORDER =  1;
    private static final boolean  SHOULD_FILTER=true;

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    private final JwtConfig jwtConfig;

    public AddRequestHeaderFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }
    public static final String USER_HEADER = "username";


    @Override
    public Object run() {
        String username = "";

        RequestContext requestContext = RequestContext.getCurrentContext();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("PRINCIPAL USERNAME AS {}", principal.toString());
        if(principal!=null){
            username = principal.toString();
        }
        log.info("USERNAME :: {}", (String) requestContext.getRequest().getAttribute("username"));
        requestContext.addZuulRequestHeader(USER_HEADER, username);
        return null;
    }

}