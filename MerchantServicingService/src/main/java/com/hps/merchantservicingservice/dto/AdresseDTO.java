package com.hps.merchantservicingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdresseDTO {
    private long adresseId;
    private Boolean isPrimary;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String email;
    private String phoneNumber;
    private String faxNumber;
}