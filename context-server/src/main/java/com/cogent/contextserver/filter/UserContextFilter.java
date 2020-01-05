package com.cogent.contextserver.filter;

import com.cogent.contextserver.security.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    private ServletContext context;

    @Autowired
    private JwtConfig jwtConfig;

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
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                this.context.log(request.getRemoteAddr() + "::Cookie::{"+cookie.getName()+","+cookie.getValue()+"}");
                log.info("HELLO WORLD ::", cookie.getDomain());
            }
        }

        String username;
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("USERNAME FROM CONTEXT :: ", servletRequest.getAttribute("username"));
        log.info("USERNAME :: {}", username);
        log.info("ENTERING USER CONTEXT WITH REQUEST HEADER VALUE AS ****: " + request.getHeader("username"));
        UserContextHolder.getContext().setUsername(request.getHeader("username"));

        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }

}
