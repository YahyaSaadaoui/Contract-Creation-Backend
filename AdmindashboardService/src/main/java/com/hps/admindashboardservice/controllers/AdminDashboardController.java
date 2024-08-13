package com.hps.admindashboardservice.controllers;

import com.hps.admindashboardservice.dto.AuthRequest;
import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.User;
import com.hps.admindashboardservice.services.AdminDashboardService;
import com.hps.admindashboardservice.services.AuthService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO userDTO) {
        return adminDashboardService.createUser(userDTO);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        return adminDashboardService.updateUser(id, userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        adminDashboardService.deleteUser(id);
        return "User deleted event published";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @Lazy
    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Lazy
    @Autowired
    private Map<String, Object> adminConfigs;
}
