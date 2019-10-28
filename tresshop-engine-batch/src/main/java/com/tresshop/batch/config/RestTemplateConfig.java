package com.tresmoto.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {


    @Value("${rest.event.connection-timeout:5000}")
    private long eventConnectionTimeOut;

    @Value("${rest.event.read-timeout:5000}")
    private long eventReadTimeOut;

    @Value("${rest.event.connection-timeout:5000}")
    private long emailConnectionTimeOut;

    @Value("${rest.event.read-timeout:5000}")
    private long emailReadTimeOut;



    @Bean
    public RestTemplate eventRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(eventConnectionTimeOut))
                .setReadTimeout(Duration.ofMillis(eventReadTimeOut))
                .build();
    }

    @Bean
    public RestTemplate emailRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(emailConnectionTimeOut))
                .setReadTimeout(Duration.ofMillis(emailReadTimeOut))
                .build();
    }


}
