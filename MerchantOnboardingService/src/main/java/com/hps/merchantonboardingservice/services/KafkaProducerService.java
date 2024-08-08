package com.hps.merchantonboardingservice.services;

import com.hps.merchantonboardingservice.events.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendContractCreatedEvent(ContractCreatedEvent event) {
        kafkaTemplate.send("ContractCreated", event);
    }

    public void sendContractUpdatedEvent(ContractUpdatedEvent event) {
        kafkaTemplate.send("ContractUpdated", event);
    }

    public void sendContractDeletedEvent(ContractDeletedEvent event) {
        kafkaTemplate.send("ContractDeleted", event);
    }

    public void sendMerchantCreatedEvent(MerchantCreatedEvent event) {
        kafkaTemplate.send("MerchantCreated", event);
    }

    public void sendMerchantUpdatedEvent(MerchantUpdatedEvent event) {
        kafkaTemplate.send("MerchantUpdated", event);
    }

    public void sendMerchantDeletedEvent(MerchantDeletedEvent event) {
        kafkaTemplate.send("MerchantDeleted", event);
    }
}
