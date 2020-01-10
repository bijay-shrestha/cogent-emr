package com.cogent.contextserver;

import com.cogent.contextserver.filter.UserContextFilter;
import com.cogent.contextserver.security.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
@ComponentScan({
		"com.cogent.contextserver.security"
})
public class ContextServerApplication{

    @Autowired
	private JwtConfig jwtConfig;

    @Bean
    public Filter userContextFilter() {
        return new UserContextFilter(jwtConfig);
    }

	public static void main(String[] args) {
		SpringApplication.run(ContextServerApplication.class, args);
	}

}
