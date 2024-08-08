package com.hps.tmsservice.events;

import com.hps.tmsservice.dto.MerchantDTO;

public class MerchantCreatedEvent {
    private MerchantDTO merchantDTO;

    public MerchantCreatedEvent() {}

    public MerchantCreatedEvent(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }

    public MerchantDTO getMerchantDTO() {
        return merchantDTO;
    }

    public void setMerchantDTO(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }
}