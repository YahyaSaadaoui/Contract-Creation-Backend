package com.hps.merchantservicingservice.events;

import com.hps.merchantservicingservice.dto.MerchantDTO;

public class MerchantCreatedEventMSS {
    private MerchantDTO merchantDTO;

    public MerchantCreatedEventMSS() {}

    public MerchantCreatedEventMSS(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }

    public MerchantDTO getMerchantDTO() {
        return merchantDTO;
    }

    public void setMerchantDTO(MerchantDTO merchantDTO) {
        this.merchantDTO = merchantDTO;
    }
}