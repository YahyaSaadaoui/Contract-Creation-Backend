package com.hps.merchantonboardingservice.events;

import com.hps.merchantonboardingservice.dto.ContractDTO;

public class ContractCreatedEvent {
    private ContractDTO contractDTO;

    public ContractCreatedEvent() {}

    public ContractCreatedEvent(ContractDTO contractDTO) {
        this.contractDTO = contractDTO;
    }

    public ContractDTO getContractDTO() {
        return contractDTO;
    }

    public void setContractDTO(ContractDTO contractDTO) {
        this.contractDTO = contractDTO;
    }
}