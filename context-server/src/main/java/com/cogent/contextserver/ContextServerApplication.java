package com.cogent.contextserver;

import com.cogent.contextserver.filter.UserContextFilter;
import com.cogent.contextserver.model.File;
import com.cogent.contextserver.repository.FileRepository;
import com.cogent.contextserver.security.JwtConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableJpaRepositories
@ComponentScan({
		"com.cogent.contextserver.security"
})
public class ContextServerApplication {

    @Resource
    FileRepository fileRepository;

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

//    @Override
//    public void run(String... args) throws Exception {
//        File file = new File("Java Notes", "Java is awesome");
//        fileRepository.saveAndFlush(file);
//
//        file.setName("Linux Notes");
//        fileRepository.saveAndFlush(file);
//    }
}
