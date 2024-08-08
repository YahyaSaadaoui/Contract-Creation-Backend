package com.hps.tmsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private long DeviceNumber;
    private String location;
    private Date status;
    private long merchantNumber;
}
