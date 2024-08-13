package com.hps.merchantonboardingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Table(name = "Merchant")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String merchantNumber;
    private String merchantName;
    private String Status;
    private Float taxRate;
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


    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<addresses> Addresses;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<activities> activities;



    // TODO : Add the fileds in the merchant creation in the service.

}
