package com.hps.merchantservicingservice.dto;


import lombok.Data;

import java.util.List;


@Data
public class MerchantDTO {
    private Long id;
    private String status;
    private Float taxRate;
    private String contactInfo;
    private String bankAccountDetails;
    private List<AdresseDTO> addresses;
    private List<ActivityDTO> activities;
}

