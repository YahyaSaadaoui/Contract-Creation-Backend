package com.hps.admindashboardservice.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
