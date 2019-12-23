package com.cogent.contextserver;

import com.cogent.contextserver.filter.UserContextFilter;
import com.cogent.genericservice.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
public class ContextServerApplication {

    @Bean
    public Filter userContextFilter() {
        return new UserContextFilter(jwtConfig());
    }

	public static void main(String[] args) {
		SpringApplication.run(ContextServerApplication.class, args);
	}

	@Bean
	public JwtConfig jwtConfig(){
		return new JwtConfig();
	}
}
