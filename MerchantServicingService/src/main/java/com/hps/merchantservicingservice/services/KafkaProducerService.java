package com.hps.merchantservicingservice.services;

import com.hps.merchantservicingservice.events.MerchantDeletedEvent;
import com.hps.merchantservicingservice.events.MerchantUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMerchantUpdatedEvent(MerchantUpdatedEvent event) {
        kafkaTemplate.send("MerchantUpdated", event);
    }
    public void sendMerchantDeletedEvent(MerchantDeletedEvent event) {
        kafkaTemplate.send("MerchantDeleted", event);
    }
}
