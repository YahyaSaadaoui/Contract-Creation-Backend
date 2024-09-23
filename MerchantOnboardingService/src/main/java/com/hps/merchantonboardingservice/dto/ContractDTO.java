package com.hps.merchantonboardingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private long ContractID;
    private String MerchantNumber;
    private String MerchantDDA;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate contractStarts;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate contractEnds;
    @Enumerated(EnumType.STRING)
    private SettlementOption settlementOption;
    @Enumerated(EnumType.STRING)
    private FeeStructure feeStructure;

}
