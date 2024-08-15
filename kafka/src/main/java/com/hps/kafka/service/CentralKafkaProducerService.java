package com.hps.kafka.service;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CentralKafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CentralKafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, Object event) {
        kafkaTemplate.send(topic, event);
    }
}
