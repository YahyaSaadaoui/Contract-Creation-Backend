package com.hps.merchantonboardingservice.mapper;

import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MerchantMapper {
    Merchant toEntity(MerchantDTO dto);
    MerchantDTO toDto(Merchant entity);
    void updateEntityFromDto(MerchantDTO dto, @MappingTarget Merchant entity);
}
