package com.cogent.edgeserver.filters;

import com.cogent.edgeserver.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseFilter extends ZuulFilter {
    private static final int  FILTER_ORDER=1;
    private static final boolean  SHOULD_FILTER=true;

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        log.info("Adding the username to the outbound headers. {}", filterUtils.getUsername());
        ctx.getResponse().addHeader(FilterUtils.USERNAME, filterUtils.getUsername());

        log.info("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }
}
