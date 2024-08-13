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
public class settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String value;
    private String description;
    private String feePercentage;
    private String currency;

    @OneToOne
    @JoinColumn(name = "user_id")
    private user user;
}
