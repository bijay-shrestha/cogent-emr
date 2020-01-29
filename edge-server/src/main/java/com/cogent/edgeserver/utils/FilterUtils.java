package com.cogent.edgeserver.utils;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    public static final String USERNAME = "username";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";

    public String getUsername(){
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(USERNAME) !=null) {
            return ctx.getRequest().getHeader(USERNAME);
        }
        else{
            return  ctx.getZuulRequestHeaders().get(USERNAME);
        }
    }

    public void setUsername(String username){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USERNAME, username);
    }
}