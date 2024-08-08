package com.hps.tmsservice.entities;

import com.hps.tmsservice.Enums.FeeStructure;
import com.hps.tmsservice.Enums.SettlementOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Table(name = "Merchant")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
