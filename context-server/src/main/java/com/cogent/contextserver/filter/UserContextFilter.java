package com.cogent.contextserver.filter;

import com.cogent.contextserver.security.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        log.info("====== INSIDE :UserContextFilter.class  WITH USERNAME AS %s ======",
                (String) request.getAttribute("AUTHENTICATED_USER"));

        String username = (String) request.getAttribute("AUTHENTICATED_USER");

        log.info("ENTERING USER CONTEXT WITH REQUEST HEADER VALUE AS ****: " + username);
        UserContextHolder.getContext().setUsername(username);

        log.info(" --- **** UserContext Username is {}", UserContext.getUsername());

        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }

}
