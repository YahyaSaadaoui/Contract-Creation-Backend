package com.hps.merchantonboardingservice.dto;

import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
public class ContractDTO {
    private long MerchantID;
    private long ContractID;
    private String MerchantDDA;
    private Date ContractStarts;
    private Date ContractEnds;
    @Enumerated(EnumType.STRING)
    private SettlementOption settlementOption;
    @Enumerated(EnumType.STRING)
    private FeeStructure feeStructure;

}
