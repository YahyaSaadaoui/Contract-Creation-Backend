package com.hps.merchantservicingservice.events;

public class MerchantDeletedEventMSS {
    private long merchantId;

    public MerchantDeletedEventMSS() {}

    public MerchantDeletedEventMSS(long merchantId) {
        this.merchantId = merchantId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }
}