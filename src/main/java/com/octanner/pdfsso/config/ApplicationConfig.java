package com.octanner.pdfsso.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    private String graphQlUrl;

    private String clientId;

    private String clientSecret;

    private String tokenUrl;

    private String createAccountUrl;

}
