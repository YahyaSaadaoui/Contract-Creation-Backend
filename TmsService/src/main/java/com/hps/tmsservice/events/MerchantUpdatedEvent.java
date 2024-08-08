package com.hps.tmsservice.events;

import com.hps.tmsservice.dto.MerchantDTO;

public class MerchantUpdatedEvent {
    private MerchantDTO merchantDTO;

    public MerchantUpdatedEvent() {}

    public MerchantUpdatedEvent(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }

    public MerchantDTO getMerchantDTO() {
        return merchantDTO;
    }

    public void setMerchantDTO(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }
}