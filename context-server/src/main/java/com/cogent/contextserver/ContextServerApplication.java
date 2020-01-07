package com.cogent.contextserver;

import com.cogent.contextserver.config.AuditorAwareImpl;
import com.cogent.genericservice.security.JwtConfig;
import com.cogent.contextserver.filter.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@ComponentScan({
		"com.cogent.genericservice.security"
})
public class ContextServerApplication {

	@Autowired
	private JwtConfig jwtConfig;


    @Bean
    public Filter userContextFilter() {
        return new UserContextFilter(jwtConfig;
    }

	public static void main(String[] args) {
		SpringApplication.run(ContextServerApplication.class, args);
	}


	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

}
