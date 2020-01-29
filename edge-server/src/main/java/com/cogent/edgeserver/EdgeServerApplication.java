package com.cogent.edgeserver;

import com.cogent.contextserver.security.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@Import(JwtConfig.class)
public class EdgeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }

}
