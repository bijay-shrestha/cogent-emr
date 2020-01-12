package com.cogent.edgeserver;

//import com.cogent.adminservice.filter.TestFilter;
import com.cogent.contextserver.security.JwtConfig;
import com.cogent.edgeserver.filters.AddRequestHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EdgeServerApplication {

    @Autowired
    private JwtConfig jwtConfig;

    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }

//    @Bean
//    public AddRequestHeaderFilter addRequestHeaderFilter(){
//        return new AddRequestHeaderFilter(jwtConfig);
//    }

    @Bean
    public AddRequestHeaderFilter addRequestHeaderFilter(){
        return new AddRequestHeaderFilter();
    }

}
