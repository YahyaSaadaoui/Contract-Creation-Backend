package com.hps.tmsservice.mapper;

import com.hps.tmsservice.dto.DeviceDTO;
import com.hps.tmsservice.entities.Device;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Device toEntity(DeviceDTO contractDTO);

    DeviceDTO toDto(Device contract);

    void updateEntityFromDto(DeviceDTO contractDTO,@MappingTarget Device contract);
}
