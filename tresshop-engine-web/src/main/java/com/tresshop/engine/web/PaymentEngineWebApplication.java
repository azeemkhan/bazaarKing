package com.tresshop.engine.web;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ServletComponentScan

@EnableAsync
@SpringBootConfiguration
@EnableEncryptableProperties
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.tresshop"})
@EnableJpaRepositories(basePackages = {"com.tresshop.engine.storage"})
@EntityScan(basePackages = {"com.tresshop.engine.storage"})
public class PaymentEngineWebApplication {


    public static void main(String[] args) {
        SpringApplication.run(PaymentEngineWebApplication.class, args);
    }

}
