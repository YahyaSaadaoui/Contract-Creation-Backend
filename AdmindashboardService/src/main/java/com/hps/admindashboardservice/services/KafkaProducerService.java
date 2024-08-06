package com.hps.admindashboardservice.services;

import com.hps.admindashboardservice.events.UserCreatedEvent;
import com.hps.admindashboardservice.events.UserDeletedEvent;
import com.hps.admindashboardservice.events.UserUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent event) {
        kafkaTemplate.send("UserCreated", event);
    }

    public void sendUserUpdatedEvent(UserUpdatedEvent event) {
        kafkaTemplate.send("UserUpdated", event);
    }

    public void sendUserDeletedEvent(UserDeletedEvent event) {
        kafkaTemplate.send("UserDeleted", event);
    }
}
