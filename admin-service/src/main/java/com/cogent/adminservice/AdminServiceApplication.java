package com.cogent.adminservice;

import com.cogent.adminservice.filter.UserContextFilter;
import com.cogent.genericservice.config.JwtConfig;
import com.cogent.persistence.util.BeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.Filter;

@SpringBootApplication
@EnableEurekaClient
@EntityScan(basePackages = {"com.cogent.persistence.model",
		"com.cogent.persistence.history"})
@Import(JwtConfig.class)
@EnableJpaRepositories
public class AdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

	@Bean
	public Filter userContextFilter() {
		return new UserContextFilter();
	}

	@Bean
	public BeanUtil beanUtil(){
		return new BeanUtil();
	}
 }