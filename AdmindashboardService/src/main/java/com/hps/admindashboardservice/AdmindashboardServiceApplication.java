package com.hps.admindashboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;

@ComponentScan(basePackages = "com.hps")
@SpringBootApplication
public class AdmindashboardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdmindashboardServiceApplication.class, args);
    }
//    @KafkaListener(id = "admindashboard", topics = "tmss")
//    public void listen(String in) {
//        System.out.println(in);
//    }

}
