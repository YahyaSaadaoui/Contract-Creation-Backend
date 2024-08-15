package com.hps.admindashboardservice.services;
import com.hps.admindashboardservice.dto.SettingsDTO;
import com.hps.admindashboardservice.dto.UserDTO;
import com.hps.admindashboardservice.entities.settings;
import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.repos.settingsRepo;
import com.hps.admindashboardservice.repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminDashboardService {
    @Autowired
    private userRepo userRepository;

    @Autowired
    private settingsRepo settingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO) {
        user user = new user();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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

        settings settings = new settings();
        SettingsDTO settingsDTO = userDTO.getSettings();
        settings.setId(settingsDTO.getId());
        settings.setName(settingsDTO.getName());
        settings.setValue(settingsDTO.getValue());
        settings.setDescription(settingsDTO.getDescription());
        settings.setFeePercentage(settingsDTO.getFeePercentage());
        settings.setCurrency(settingsDTO.getCurrency());
        settings.setUser(user);


        user savedUser = userRepository.save(user);
        settings.setUser(savedUser);
        settingsRepository.save(settings);

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

        // Convert SettingsDTO to Settings entity
        settings settings = userDTO.getSettings() != null ? settingsRepository.findById(userDTO.getSettings().getId()).orElse(new settings()) : new settings();
        settings.setName(userDTO.getSettings().getName());
        settings.setValue(userDTO.getSettings().getValue());
        settings.setDescription(userDTO.getSettings().getDescription());
        settings.setFeePercentage(userDTO.getSettings().getFeePercentage());
        settings.setCurrency(userDTO.getSettings().getCurrency());
        settings.setUser(user); // Set bi-directional relationship

        user.setSettings(settings);

        settingsRepository.save(settings); // Save settings first
        userRepository.save(user); // Save user with the updated settings

        return userDTO;
    }

    public void deleteUser(long id) {
        user user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        settings settings = user.getSettings();
        if (settings != null) {
            settingsRepository.delete(settings); // Remove settings
        }
        userRepository.delete(user); // Remove user
    }
}
