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


    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<addresses> Addresses;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<activities> activities;


}
