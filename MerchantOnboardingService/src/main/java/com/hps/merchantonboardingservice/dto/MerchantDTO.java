package com.hps.merchantonboardingservice.dto;

import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO {
    private Long id;
    private String merchantNumber;
    private String merchantName;
    private String Status; /* 3 status : Normal ; Suspended   */
    private Float taxRate;
    private String contactInfo;
    private String Adress;
    private String email;
    private String bankAccountDetails;
    private String contractStatus;
    private Date updated_at;
    /*Look IAM User managment system*/
    private Date deleted_at;
    private Date deleted_by;
    private Date created_by;
    private Date updated_by;

    @Enumerated(EnumType.STRING)
    private SettlementOption settlementOption;
    @Enumerated(EnumType.STRING)
    private FeeStructure feeStructure;

    private List<AdresseDTO> addresses = new ArrayList<>();
    private List<ActivitiesDTO> activities = new ArrayList<>();
}

