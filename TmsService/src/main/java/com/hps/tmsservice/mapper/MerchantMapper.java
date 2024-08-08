package com.hps.tmsservice.mapper;

import com.hps.tmsservice.dto.MerchantDTO;
import com.hps.tmsservice.entities.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MerchantMapper {
    Merchant toEntity(MerchantDTO dto);
    MerchantDTO toDto(Merchant entity);
    void updateEntityFromDto(MerchantDTO dto, @MappingTarget Merchant entity);
}
