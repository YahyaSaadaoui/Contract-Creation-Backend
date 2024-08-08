package com.hps.tmsservice.services;

import com.hps.tmsservice.events.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendContractCreatedEvent(DeviceCreatedEvent event) {
        kafkaTemplate.send("ContractCreated", event);
    }

    public void sendContractUpdatedEvent(DeviceUpdatedEvent event) {
        kafkaTemplate.send("ContractUpdated", event);
    }

    public void sendContractDeletedEvent(DeviceDeletedEvent event) {
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
