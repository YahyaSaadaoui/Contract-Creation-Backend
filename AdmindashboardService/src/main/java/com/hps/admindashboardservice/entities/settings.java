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
    // TODO: I will need to Add more fields later on dev stage after the meeting with the mohamed and the team
}
