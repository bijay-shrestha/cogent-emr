package com.cogent.adminservice;

import com.cogent.adminservice.audit.AuditorAwareImpl;
import com.cogent.adminservice.filter.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.Filter;

@SpringBootApplication
@EnableEurekaClient
@EntityScan(basePackages = {"com.cogent.persistence.model", "com.cogent.adminservice.model"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

	@Bean
	public Filter userContextFilter() {
		return new UserContextFilter();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}