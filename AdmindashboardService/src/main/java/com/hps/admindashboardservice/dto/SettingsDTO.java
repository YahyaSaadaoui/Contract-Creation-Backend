package com.hps.admindashboardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDTO {
    private long id;
    private String name;
    private String value;
    private String description;
    private String feePercentage;
    private String currency;
}
