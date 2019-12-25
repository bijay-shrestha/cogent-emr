package com.cogent.edgeserver;

import com.cogent.edgeserver.filter.AddRequestHeaderFilter;
import com.cogent.genericservice.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EdgeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServerApplication.class, args);
	}

	@Bean
	public AddRequestHeaderFilter addRequestHeaderFilter(JwtConfig jwtConfig) {
		return new AddRequestHeaderFilter(jwtConfig);
	}

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}



}
