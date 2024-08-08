package com.hps.tmsservice.events;


import com.hps.tmsservice.dto.DeviceDTO;

public class DeviceUpdatedEvent {
    private DeviceDTO deviceDTO;

    public DeviceUpdatedEvent() {}

    public DeviceUpdatedEvent(DeviceDTO contractDTO) {
        this.deviceDTO = contractDTO;
    }

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }
}