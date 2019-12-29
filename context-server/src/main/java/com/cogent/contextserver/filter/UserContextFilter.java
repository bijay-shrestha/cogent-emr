package com.cogent.contextserver.filter;

import com.cogent.genericservice.security.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    private ServletContext context;

    private final JwtConfig jwtConfig;

    public UserContextFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log(" ====== User Context Filter Initialized ======");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String name = params.nextElement();
            String value = request.getParameter(name);
            this.context.log(request.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");
        }

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                this.context.log(request.getRemoteAddr() + "::Cookie::{"+cookie.getName()+","+cookie.getValue()+"}");
                log.info("HELLO WORLD ::", cookie.getDomain());
            }
        }

        log.info("USERNAME FROM CONTEXT :: ", servletRequest.getAttribute("username"));
        log.info("ENTERING USER CONTEXT WITH REQUEST HEADER VALUE AS ****: " + request.getHeader("username"));
        UserContextHolder.getContext().setUsername(request.getHeader("username"));

        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }

}
