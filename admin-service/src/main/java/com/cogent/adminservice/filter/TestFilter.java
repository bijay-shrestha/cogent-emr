//package com.cogent.adminservice.filter;
//
//
//import com.cogent.contextserver.filter.UserContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Slf4j
////@WebFilter(urlPatterns= {"/admin"})
//@Order(3)
//@Component
//public class TestFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String username;
//        Object principal =
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        log.info("URI {} FROM {} WITH USER-CONTEXT USERNAME AS {} AND SECURITY-CONTEXT-USERNAME AS {}",
//                request.getMethod(),
//                request.getRequestURI(),
//                UserContext.getUsername(),
//                username);
//
//        log.info("URI {} FROM {} WITH USER-CONTEXT USERNAME AS {}",
//                request.getMethod(),
//                request.getRequestURI(),
//                UserContext.getUsername());
//
//        String URI  = request.getRequestURI();
//
//        filterChain.doFilter(request, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//}
