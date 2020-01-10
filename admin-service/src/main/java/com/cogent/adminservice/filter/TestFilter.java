package com.cogent.adminservice.filter;


import com.cogent.contextserver.filter.UserContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        log.info("URI ***** {} from {}", request.getMethod(), request.getRequestURI());
        String URI  = request.getRequestURI();

        log.info(" +++ TEST FILTER ---- **** {}", UserContext.getUsername());

//        String username;
//        Object principal =
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }

        log.info("ENTERING TEST FILTER WITH REQUEST HEADER VALUE AS ****: " + "bijay");


        log.info(" --- **** TEST-FILTER ---- UserContext Username is {}", UserContext.getUsername());
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
