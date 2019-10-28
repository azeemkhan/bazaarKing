package com.tresmoto.batch;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableEncryptableProperties
@SpringBootApplication
public class PaymentEngineBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentEngineBatchApplication.class, args);
    }
}
