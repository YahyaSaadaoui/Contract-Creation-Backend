package com.hps.admindashboardservice.services;
import com.hps.admindashboardservice.dto.SettingsDTO;
import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.settings;
import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.events.UserCreatedEvent;
import com.hps.admindashboardservice.events.UserDeletedEvent;
import com.hps.admindashboardservice.events.UserUpdatedEvent;
import com.hps.admindashboardservice.repos.settingsRepo;
import com.hps.admindashboardservice.repos.userRepo;
//import com.hps.kafka.service.CentralKafkaProducerService;
import com.hps.kafka.service.CentralKafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDashboardService {
    @Autowired
    private userRepo userRepository;

    @Autowired
    private settingsRepo settingsRepository;

    @Autowired
    private CentralKafkaProducerService kafkaProducerService;


    public UserDTO createUser(UserDTO userDTO) {
        user user = new user();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPermissions(userDTO.getPermissions());
        user.setStatus(userDTO.getStatus());
        user.setCreated_at(userDTO.getCreated_at());
        user.setUpdated_at(userDTO.getUpdated_at());
        user.setDeleted_at(userDTO.getDeleted_at());
        user.setDeleted_by(userDTO.getDeleted_by());
        user.setCreated_by(userDTO.getCreated_by());
        user.setUpdated_by(userDTO.getUpdated_by());
        user.setLast_login_attempt_time(userDTO.getLast_login_attempt_time());

        // Create and set Settings
        settings settings = userDTO.getSettings();
        settings.setUser(user);
        user.setSettings(settings);

        settingsRepository.save(settings); // Save settings first
        userRepository.save(user); // Save user with the reference to settings

        return userDTO;
    }

    public UserDTO updateUser(UserDTO userDTO) {
        user user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPermissions(userDTO.getPermissions());
        user.setStatus(userDTO.getStatus());
        user.setCreated_at(userDTO.getCreated_at());
        user.setUpdated_at(userDTO.getUpdated_at());
        user.setDeleted_at(userDTO.getDeleted_at());
        user.setDeleted_by(userDTO.getDeleted_by());
        user.setCreated_by(userDTO.getCreated_by());
        user.setUpdated_by(userDTO.getUpdated_by());
        user.setLast_login_attempt_time(userDTO.getLast_login_attempt_time());

        // Update settings
        settings settings = userDTO.getSettings();
        settings.setUser(user);
        user.setSettings(settings);

        settingsRepository.save(settings); // Save settings first
        userRepository.save(user); // Save user with the updated settings

        return userDTO;
    }
}
