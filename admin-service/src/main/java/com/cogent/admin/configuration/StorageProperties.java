package com.cogent.admin.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
@Setter
@Getter
public class StorageProperties {
    private String location = "cogent-uploads";
}