package com.hps.merchantonboardingservice.events;

public class MerchantDeletedEvent {
    private long merchantId;

    public MerchantDeletedEvent() {}

    public MerchantDeletedEvent(long merchantId) {
        this.merchantId = merchantId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }
}