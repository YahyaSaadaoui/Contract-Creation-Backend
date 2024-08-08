package com.hps.kafka.controller;

import com.hps.kafka.service.KafkaManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/topics")
public class KafkaManagementController {

    @Autowired
    @Lazy
    private KafkaManagementService kafkaManagementService;

    @PostMapping("/createTopic")
    public String createTopic(@RequestParam String topicName, @RequestParam int numPartitions, @RequestParam short replicationFactor) {
        kafkaManagementService.createTopic(topicName, numPartitions, replicationFactor);
        return "Topic created";
    }

    @DeleteMapping("/deleteTopic")
    public String deleteTopic(@RequestParam String topicName) throws ExecutionException, InterruptedException {
        kafkaManagementService.deleteTopic(topicName);
        return "Topic deleted";
    }

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestParam String topicName, @RequestParam String groupId) {
        kafkaManagementService.subscribeToTopic(topicName, groupId);
    }

    @PostMapping("/produce")
    public void produceMessage(@RequestParam String topicName, @RequestParam String key, @RequestBody Object value) {
        kafkaManagementService.produceMessage(topicName, key, value);
    }
}