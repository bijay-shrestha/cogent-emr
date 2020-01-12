package com.cogent.edgeserver.filters;

import com.cogent.authservice.security.CustomUserDetail;
import com.cogent.persistence.model.Admin;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

@Slf4j
public class AddRequestHeaderFilter extends ZuulFilter {

    public static final String USER_HEADER = "X-User-Header";
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }
    @Override
    public int filterOrder() {
        return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
    }
    @Override
    public boolean shouldFilter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() != null;
    }
    @Override
    public Object run() {
        String username="";
        RequestContext requestContext = RequestContext.getCurrentContext();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetail) {
			username = ((Admin)principal).getUsername();
		} else {
			username = principal.toString();
		}
		log.info("USERNAME :: {}", username);
        requestContext.addZuulRequestHeader(USER_HEADER, username);
        return null;
    }

}