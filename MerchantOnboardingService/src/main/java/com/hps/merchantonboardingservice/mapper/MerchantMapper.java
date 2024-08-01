package com.hps.merchantonboardingservice.mapper;

import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantDTO toDto(Merchant merchant);
    Merchant toEntity(MerchantDTO merchantDTO);
    void updateEntityFromDto(MerchantDTO merchantDTO, @MappingTarget Merchant merchant);
}
