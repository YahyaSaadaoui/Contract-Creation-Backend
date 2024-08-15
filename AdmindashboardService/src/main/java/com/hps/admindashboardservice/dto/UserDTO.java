package com.hps.admindashboardservice.dto;


import com.hps.admindashboardservice.entities.settings;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String permissions;
    private String status;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String deleted_by;
    private String created_by;
    private String updated_by;
    private String last_login_attempt_time;
    private SettingsDTO settings;
}
