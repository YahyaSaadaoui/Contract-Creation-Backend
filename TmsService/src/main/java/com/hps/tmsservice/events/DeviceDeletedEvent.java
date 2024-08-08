package com.hps.tmsservice.events;

public class DeviceDeletedEvent {
    private long deviceId;

    public DeviceDeletedEvent() {}

    public DeviceDeletedEvent(long contractId) {
        this.deviceId = contractId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
}
