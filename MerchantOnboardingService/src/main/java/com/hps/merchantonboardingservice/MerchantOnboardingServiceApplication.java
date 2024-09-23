package com.hps.merchantonboardingservice;

import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.events.MerchantCreatedEvent;
import com.hps.merchantonboardingservice.events.MerchantDeletedEvent;
import com.hps.merchantonboardingservice.events.MerchantUpdatedEvent;
import com.hps.merchantonboardingservice.services.MerchantOnboardingService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@ComponentScan(basePackages = "com.hps")
@SpringBootApplication
public class MerchantOnboardingServiceApplication {

//    @Autowired
//    private MerchantOnboardingService merchantOnboardingService;

    public static void main(String[] args) {
        SpringApplication.run(MerchantOnboardingServiceApplication.class, args);
    }

//    @KafkaListener(id = "merchantUpdatedListener", topics = "ContractCreationTopic", groupId = "merchant-servicing-service")
//    public void listenForMerchantUpdatedEvent(MerchantDTO merchantDTO) {
//        merchantOnboardingService.updateMerchant(merchantDTO.getId(), merchantDTO);
//    }
//
//    @KafkaListener(id = "merchantDeletedListener", topics = "ContractCreationTopic", groupId = "merchant-servicing-service")
//    public void listenForMerchantDeletedEvent(Long merchantId) {
//        merchantOnboardingService.deleteMerchant(merchantId);
//    }

}
