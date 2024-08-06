package com.hps.admindashboardservice.services;
import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.events.UserCreatedEvent;
import com.hps.admindashboardservice.events.UserDeletedEvent;
import com.hps.admindashboardservice.events.UserUpdatedEvent;
import com.hps.admindashboardservice.repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDashboardService {

    @Autowired
    private userRepo userRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public user createUser(UserDTO userDTO) {
        user newUser = new user(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole(),
                userDTO.getPermissions(),
                userDTO.getStatus(),
                userDTO.getCreated_at(),
                userDTO.getUpdated_at(),
                userDTO.getDeleted_at(),
                userDTO.getDeleted_by(),
                userDTO.getCreated_by(),
                userDTO.getUpdated_by(),
                userDTO.getLast_login_attempt_time()
        );

        user savedUser = userRepository.save(newUser);
        kafkaProducerService.sendUserCreatedEvent(new UserCreatedEvent(userDTO));
        return savedUser;
    }

    public user updateUser(long id, UserDTO userDTO) {
        Optional<user> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            user existingUser = existingUserOpt.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setRole(userDTO.getRole());
            existingUser.setPermissions(userDTO.getPermissions());
            existingUser.setStatus(userDTO.getStatus());
            existingUser.setCreated_at(userDTO.getCreated_at());
            existingUser.setUpdated_at(userDTO.getUpdated_at());
            existingUser.setDeleted_at(userDTO.getDeleted_at());
            existingUser.setDeleted_by(userDTO.getDeleted_by());
            existingUser.setCreated_by(userDTO.getCreated_by());
            existingUser.setUpdated_by(userDTO.getUpdated_by());
            existingUser.setLast_login_attempt_time(userDTO.getLast_login_attempt_time());

            user updatedUser = userRepository.save(existingUser);
            kafkaProducerService.sendUserUpdatedEvent(new UserUpdatedEvent(userDTO));
            return updatedUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
        kafkaProducerService.sendUserDeletedEvent(new UserDeletedEvent(id));
    }
}
