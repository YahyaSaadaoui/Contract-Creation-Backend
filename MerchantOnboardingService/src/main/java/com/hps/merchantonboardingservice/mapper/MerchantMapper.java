package com.hps.merchantonboardingservice.mapper;

import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring") // Inject other mappers : uses = {AddressMapper.class, ActvitiesMapper.class}
public interface MerchantMapper {

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "merchantNumber", source = "merchantNumber")
//    @Mapping(target = "merchantName", source = "merchantName")
//    @Mapping(target = "status", source = "status")
//    @Mapping(target = "taxRate", source = "taxRate")
//    @Mapping(target = "accountBalance", source = "accountBalance")
//    @Mapping(target = "contactInfo", source = "contactInfo")
//    @Mapping(target = "bankAccountDetails", source = "bankAccountDetails")
//    @Mapping(target = "contractStatus", source = "contractStatus")
//    @Mapping(target = "updatedAt", source = "updatedAt")
//    @Mapping(target = "deletedAt", source = "deletedAt")
//    @Mapping(target = "deletedBy", source = "deletedBy")
//    @Mapping(target = "createdBy", source = "createdBy")
//    @Mapping(target = "updatedBy", source = "updatedBy")
//    @Mapping(target = "settlementOption", source = "settlementOption")
//    @Mapping(target = "feeStructure", source = "feeStructure")
//    @Mapping(target = "addresses", qualifiedByName = "toDtoList")
//    @Mapping(target = "activities", qualifiedByName = "toDtoList")
//   MerchantDTO toDto(Merchant entity);
//
//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "merchantNumber", source = "merchantNumber")
//    @Mapping(target = "merchantName", source = "merchantName")
//    @Mapping(target = "status", source = "status")
//    @Mapping(target = "taxRate", source = "taxRate")
//    @Mapping(target = "accountBalance", source = "accountBalance")
//    @Mapping(target = "contactInfo", source = "contactInfo")
//    @Mapping(target = "bankAccountDetails", source = "bankAccountDetails")
//    @Mapping(target = "contractStatus", source = "contractStatus")
//    @Mapping(target = "updatedAt", source = "updatedAt")
//    @Mapping(target = "deletedAt", source = "deletedAt")
//    @Mapping(target = "deletedBy", source = "deletedBy")
//    @Mapping(target = "createdBy", source = "createdBy")
//    @Mapping(target = "updatedBy", source = "updatedBy")
//    @Mapping(target = "settlementOption", source = "settlementOption")
//    @Mapping(target = "feeStructure", source = "feeStructure")
//    @Mapping(target = "addresses", qualifiedByName = "toEntityList")
//    @Mapping(target = "activities", qualifiedByName = "toEntityList")
//    Merchant toEntity(MerchantDTO dto);
//
//    @IterableMapping(qualifiedByName = "toDto")
//    List<AdresseDTO> addressToDtoList(List<addresses> addresses);
//
//    @IterableMapping(qualifiedByName = "toDto")
//    List<ActivitiesDTO> activityToDtoList(List<activities> activities);
//
//   void updateEntityFromDto(MerchantDTO dto, @MappingTarget Merchant entity);
    Merchant toEntity(MerchantDTO dto);
    MerchantDTO toDto(Merchant entity);
    void updateEntityFromDto(MerchantDTO dto, @MappingTarget Merchant entity);
}
