package com.hps.merchantservicingservice.dto;


import com.hps.merchantservicingservice.Enums.FeeStructure;
import com.hps.merchantservicingservice.Enums.SettlementOption;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Date;

@Data
public class MerchantDTO {
    private long merchantId;
    private String merchantNumber;
    private String merchantName;
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


}

