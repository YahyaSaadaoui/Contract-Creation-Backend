package com.hps.merchantonboardingservice.mapper;

import com.hps.merchantonboardingservice.dto.ContractDTO;
import com.hps.merchantonboardingservice.entities.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    Contract toEntity(ContractDTO contractDTO);

    ContractDTO toDto(Contract contract);

    void updateEntityFromDto(ContractDTO contractDTO,@MappingTarget Contract contract);
}
