package com.hps.merchantonboardingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import com.hps.merchantonboardingservice.entities.activities;
import com.hps.merchantonboardingservice.entities.addresses;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
    private Long id;// TO send in the dto for Transacation Control
    private String merchantNumber;// TO send in the dto for Transacation Control
    private String merchantName;// TO send in the dto for Transacation Control
    private String Status;// TO send in the dto for Transacation Control
    private Float taxRate;// TO send in the dto for Transacation Control
    private Float accountBalance;// TO send in the dto for Settlement
    private String contactInfo;
    private String bankAccountDetails;
    private String contractStatus;
    private Date updated_at;
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

