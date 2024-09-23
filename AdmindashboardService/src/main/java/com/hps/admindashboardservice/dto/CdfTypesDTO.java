package com.hps.admindashboardservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CdfTypesDTO {
    private long id;
    private String description;
    private String BREAKPOINT_1;
    private String BREAKPOINT_2;
    private String BREAKPOINT_3;
}
