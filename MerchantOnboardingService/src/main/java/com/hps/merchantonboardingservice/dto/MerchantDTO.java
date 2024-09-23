package com.hps.merchantonboardingservice.dto;

import com.hps.merchantonboardingservice.Enums.FeeStructure;
import com.hps.merchantonboardingservice.Enums.SettlementOption;
import com.hps.merchantonboardingservice.entities.activities;
import com.hps.merchantonboardingservice.entities.addresses;
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
    private Long id;// TO send in the dto for Transacation Control
    private String merchantNumber;// TO send in the dto for Transacation Control
    private String merchantName;// TO send in the dto for Transacation Control
    private String status;// TO send in the dto for Transacation Control
    private Float taxRate;// TO send in the dto for Transacation Control
    private Float accountBalance;// TO send in the dto for Settlement
    private String bankAccountDetails;
    private String contractStatus;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private String deleted_by;
    private String created_by;
    private String updated_by;
    @Enumerated(EnumType.STRING)
    private SettlementOption settlementOption;
    @Enumerated(EnumType.STRING)
    private FeeStructure feeStructure;
    private List<AdresseDTO> addresses = new ArrayList<>();
    private List<ActivitiesDTO> activities = new ArrayList<>();

//
//    // Custom getter and setter for the mapper to work:
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getMerchantNumber() {
//        return merchantNumber;
//    }
//
//    public void setMerchantNumber(String merchantNumber)
//    {
//        this.merchantNumber = merchantNumber;
//    }
//
//    public String getMerchantName() {
//        return merchantName;
//    }
//
//    public void setMerchantName(String merchantName) {
//        this.merchantName = merchantName;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Float getTaxRate() {
//        return taxRate;
//    }
//
//    public void setTaxRate(Float taxRate) {
//        this.taxRate = taxRate;
//    }
//
//    public Float getAccountBalance() {
//        return accountBalance;
//    }
//
//    public void setAccountBalance(Float accountBalance) {
//        this.accountBalance = accountBalance;
//
//    }
//
//    public String getContactInfo() {
//        return contactInfo;
//    }
//
//    public void setContactInfo(String contactInfo) {
//        this.contactInfo = contactInfo;
//    }
//
//    public String getBankAccountDetails() {
//        return bankAccountDetails;
//    }
//
//    public void setBankAccountDetails(String bankAccountDetails) {
//        this.bankAccountDetails = bankAccountDetails;
//
//    }
//
//    public String getContractStatus() {
//        return contractStatus;
//    }
//
//    public void setContractStatus(String contractStatus) {
//        this.contractStatus = contractStatus;
//
//    }
//
//    public Date getUpdatedAt() { // camelCase
//        return updated_at;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updated_at = updatedAt;
//    }
//
//    public Date getDeletedAt() {
//        return deleted_at;
//    }
//
//    public void setDeletedAt(Date deletedAt) {
//        this.deleted_at = deletedAt;
//    }
//
//    public Date getDeletedBy()
//    {
//        return deleted_by;
//    }
//
//    public void setDeletedBy(Date deletedBy) {
//        this.deleted_by = deletedBy;
//    }
//
//    public Date getCreatedBy() {
//        return created_by;
//    }
//
//    public void setCreatedBy(Date createdBy) {
//        this.created_by = createdBy;
//    }
//
//    public Date getUpdatedBy() {
//        return updated_by;
//    }
//
//    public void setUpdatedBy(Date updatedBy) {
//        this.updated_by = updatedBy;
//    }
//
//    public SettlementOption getSettlementOption() {
//        return settlementOption;
//    }
//
//    public void setSettlementOption(SettlementOption settlementOption) {
//        this.settlementOption = settlementOption;
//    }
//
//    public FeeStructure getFeeStructure() {
//        return feeStructure;
//    }
//
//    public void setFeeStructure(FeeStructure feeStructure) {
//        this.feeStructure = feeStructure;
//
//    }
//
//    public List<AdresseDTO> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(List<AdresseDTO> addresses) {
//        addresses = addresses;
//    }
//
//    public List<ActivitiesDTO> getActivities() {
//        return activities;
//    }
//
//    public void setActivities(List<ActivitiesDTO> activities) {
//        this.activities = activities;
//
//    }

}

