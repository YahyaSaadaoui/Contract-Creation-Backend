package com.hps.admindashboardservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "settings")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class user {
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
