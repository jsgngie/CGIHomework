package com.cinema.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${base.api.url}")
    private String baseApiUrl;

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
