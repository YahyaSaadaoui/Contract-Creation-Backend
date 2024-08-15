package com.hps.merchantservicingservice;

import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.services.MerchantServicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;

@ComponentScan(basePackages = "com.hps")
@SpringBootApplication
public class MerchantServicingServiceApplication {

//    @Autowired
//    MerchantServicingService merchantServicingService;
    public static void main(String[] args) {
        SpringApplication.run(MerchantServicingServiceApplication.class, args);
    }

//    @KafkaListener(id = "merchantCreatedListener", topics = "ContractCreationTopic", groupId = "merchant-servicing-service")
//    public void listenForMerchantCreatedEventMSS(MerchantDTO merchantDTO) {
//        merchantServicingService.createMerchant(merchantDTO);
//    }
//
//    @KafkaListener(id = "merchantUpdatedListener", topics = "ContractCreationTopic", groupId = "merchant-servicing-service")
//    public void listenForMerchantUpdatedEventMSS(MerchantDTO merchantDTO) {
//        merchantServicingService.updateMerchant(merchantDTO.getId(), merchantDTO);
//    }
//
//    @KafkaListener(id = "merchantDeletedListener", topics = "ContractCreationTopic", groupId = "merchant-servicing-service")
//    public void listenForMerchantDeletedEventMSS(Long merchantId) {
//        merchantServicingService.deleteMerchant(merchantId);
//    }

}
