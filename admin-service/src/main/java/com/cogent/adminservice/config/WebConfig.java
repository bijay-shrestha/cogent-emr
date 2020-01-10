package com.cogent.adminservice.config;

import com.cogent.contextserver.security.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({
        "com.cogent.contextserver.security",
        "com.cogent.adminservice.filter"
})
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtConfig jwtConfig;
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
//         resolvers.add(new WithUserHandlerMethodArgumentResolver(jwtConfig));
//    }
}
