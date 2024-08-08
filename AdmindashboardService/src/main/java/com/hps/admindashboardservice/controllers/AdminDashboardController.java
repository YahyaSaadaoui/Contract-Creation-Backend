package com.hps.admindashboardservice.controllers;

import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.services.AdminDashboardService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AdminDashboardController {
    @Autowired
    private AdminDashboardService adminDashboardService;

    @PostMapping("/userCreated")
    public user userCreated(@RequestBody UserDTO userDTO) {
        return adminDashboardService.createUser(userDTO);
    }

    @PutMapping("/userUpdated/{id}")
    public user userUpdated(@PathVariable long id, @RequestBody UserDTO userDTO) {
        return adminDashboardService.updateUser(id, userDTO);
    }

    @DeleteMapping("/userDeleted/{id}")
    public String userDeleted(@PathVariable long id) {
        adminDashboardService.deleteUser(id);
        return "User deleted event published";
    }
    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Autowired
    private Map<String, Object> adminConfigs;

    @PostConstruct
    public void createTopic() {
        String topicName = "admindashboard"; // Set the topic name here
        int numPartitions = 1; // Adjust as needed
        short replicationFactor = 1; // Adjust as needed

        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(newTopic);

    }
}
