package com.hps.admindashboardservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cdf_types")
@NoArgsConstructor
@AllArgsConstructor
public class CdfTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String BREAKPOINT_1;
    private String BREAKPOINT_2;
    private String BREAKPOINT_3;

}
