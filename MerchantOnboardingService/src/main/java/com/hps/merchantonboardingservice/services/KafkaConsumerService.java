package com.hps.merchantonboardingservice.services;
import com.hps.merchantonboardingservice.dto.MerchantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class KafkaConsumerService {
//    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
//
//    @KafkaListener(topics = "merchant-created", groupId = "group_id")
//    public void consumeMerchantCreated(MerchantDTO merchantDTO) {
//        logger.info("Consumed Merchant Created Event: {}", merchantDTO);
//    }
//
//    @KafkaListener(topics = "merchant-updated", groupId = "group_id")
//    public void consumeMerchantUpdated(MerchantDTO merchantDTO) {
//        logger.info("Consumed Merchant Updated Event: {}", merchantDTO);
//    }
//
//    @KafkaListener(topics = "merchant-deleted", groupId = "group_id")
//    public void consumeMerchantDeleted(Long merchantId) {
//        logger.info("Consumed Merchant Deleted Event: {}", merchantId);
//    }
//    @KafkaListener(topics = "merchant-servicng-service", groupId = "merchantGroup")
//    public void listenMerchantServicingService(Object message) {
//        System.out.printf("Consumed message from merchant-servicng-service: %s%n", message);
//    }
//
//    @KafkaListener(topics = "tms-service", groupId = "merchantGroup")
//    public void listenTmsService(Object message) {
//        System.out.printf("Consumed message from tms-service: %s%n", message);
//    }
}