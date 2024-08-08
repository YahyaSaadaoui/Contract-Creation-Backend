package com.hps.tmsservice.events;


import com.hps.tmsservice.dto.DeviceDTO;

public class DeviceCreatedEvent {
    private DeviceDTO deviceDTO;

    public DeviceCreatedEvent() {}

    public DeviceCreatedEvent(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }
}