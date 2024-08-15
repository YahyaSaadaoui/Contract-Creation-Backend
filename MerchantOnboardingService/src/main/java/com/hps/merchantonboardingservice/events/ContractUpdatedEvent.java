package com.hps.merchantonboardingservice.events;

import com.hps.merchantonboardingservice.dto.ContractDTO;

public class ContractUpdatedEvent {
    private ContractDTO contractDTO;

    public ContractUpdatedEvent() {}

    public ContractUpdatedEvent(ContractDTO contractDTO) {
        this.contractDTO = contractDTO;
    }

    public ContractDTO getContractDTO() {
        return contractDTO;
    }

    public void setContractDTO(ContractDTO contractDTO) {
        this.contractDTO = contractDTO;
    }
}