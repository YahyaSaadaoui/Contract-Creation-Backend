package com.hps.admindashboardservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PermissionDTO {

    private Long id;
    private String label;
    public PermissionDTO(String label) {
        this.label = label;
    }
}
