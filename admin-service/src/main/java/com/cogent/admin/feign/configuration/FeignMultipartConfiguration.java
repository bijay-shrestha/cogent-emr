package com.cogent.admin.feign.configuration;

import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;

/*CONFIGURATION TO SUPPORT PASSING OF MULTIPART FILES ACROSS MICROSERVICE MODULES*/
public class FeignMultipartConfiguration {

    @Bean
    public Encoder feignFormEncoder() {
        return new FeignSpringFormEncoder();
    }
}