package com.hps.merchantservicingservice.events;

import com.hps.merchantservicingservice.dto.MerchantDTO;

public class MerchantUpdatedEventMSS {
    private MerchantDTO merchantDTO;

    public MerchantUpdatedEventMSS() {}

    public MerchantUpdatedEventMSS(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }

    public MerchantDTO getMerchantDTO() {
        return merchantDTO;
    }

    public void setMerchantDTO(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }
}