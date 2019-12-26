package com.cogent.cogentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CogentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CogentServerApplication.class, args);
	}

}
