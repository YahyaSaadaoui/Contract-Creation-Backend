package com.hps.merchantservicingservice.dto;


import lombok.Data;

import java.sql.Date;
import java.util.List;


@Data
public class MerchantDTO {
    private Long id;
    private String merchantNumber;
    private String merchantName;
    private String status;
    private Float taxRate;
    private String bankAccountDetails;
    private String contractStatus;
    private Float accountBalance;
    private Date updated_at;
    private String updated_by;
    private List<AdresseDTO> addresses;
    private List<ActivityDTO> activities;
}

