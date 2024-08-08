package com.hps.merchantonboardingservice.events;

import com.hps.merchantonboardingservice.dto.ContractDTO;

public class ContractDeletedEvent {
    private long contractId;

    public ContractDeletedEvent() {}

    public ContractDeletedEvent(long contractId) {
        this.contractId = contractId;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }
}
