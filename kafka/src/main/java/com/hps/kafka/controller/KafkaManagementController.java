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

    // New Way now in the main app
//    @PostMapping("/createTopics")
//    public String createTopics() {
//        kafkaManagementService.createTopic("ContractCreationTopic", 3, (short) 1);
//        kafkaManagementService.createTopic("AdminDashBoardTopic", 3, (short) 1);
//        return "Topics created";
//    }

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