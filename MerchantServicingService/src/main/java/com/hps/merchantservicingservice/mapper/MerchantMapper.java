package com.hps.merchantservicingservice.mapper;

import com.hps.merchantservicingservice.dto.MerchantDTO;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantDTO toEntity(MerchantDTO dto);
    MerchantDTO toDto(MerchantDTO entity);
    void updateEntityFromDto(MerchantDTO dto, @MappingTarget MerchantDTO entity);
}
