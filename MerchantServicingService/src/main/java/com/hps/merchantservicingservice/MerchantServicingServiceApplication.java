package com.hps.merchantservicingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MerchantServicingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantServicingServiceApplication.class, args);
    }

}
