package com.cogent.edgeserver.configuration;

import com.cogent.contextserver.filter.UserContextFilter;
import com.cogent.contextserver.security.JwtConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdgeConfig {

    @Bean
    public JwtConfig jwtConfig(){
        return new JwtConfig();
    }

    @Bean
    public FilterRegistrationBean <UserContextFilter> filterRegistrationBean() {
        FilterRegistrationBean < UserContextFilter > registrationBean = new FilterRegistrationBean();
        UserContextFilter customURLFilter = new UserContextFilter(jwtConfig());

        registrationBean.setFilter(customURLFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2); //set precedence
        return registrationBean;
    }

}
