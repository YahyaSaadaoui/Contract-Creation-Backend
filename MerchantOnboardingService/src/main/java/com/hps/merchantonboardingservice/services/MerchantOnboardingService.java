package com.hps.merchantonboardingservice.services;

import com.hps.merchantonboardingservice.dto.ContractDTO;
import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Contract;
import com.hps.merchantonboardingservice.entities.Merchant;
import com.hps.merchantonboardingservice.entities.activities;
import com.hps.merchantonboardingservice.entities.addresses;
import com.hps.merchantonboardingservice.mapper.MerchantMapper;
import com.hps.merchantonboardingservice.repos.ContractRepo;
import com.hps.merchantonboardingservice.repos.MerchantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantOnboardingService {

    @Autowired
    private MerchantRepo merchantRepository;

    @Autowired
    private ContractRepo contractRepository;

//    @Autowired
//    private CentralKafkaProducerService kafkaProducerService;

    @Autowired
    private MerchantMapper merchantMapper;

    public Merchant createMerchant(MerchantDTO merchantDTO) {
        Merchant newMerchant = merchantMapper.toEntity(merchantDTO);

        List<addresses> addressesList = (merchantDTO.getAddresses() != null) ?
                merchantDTO.getAddresses().stream()
                        .map(adresseDTO -> {
                            addresses address = new addresses();
                            address.setStreet(adresseDTO.getStreet());
                            address.setCity(adresseDTO.getCity());
                            address.setState(adresseDTO.getState());
                            address.setCountry(adresseDTO.getCountry());
                            address.setZipCode(adresseDTO.getZipCode());
                            address.setEmail(adresseDTO.getEmail());
                            address.setPhoneNumber(adresseDTO.getPhoneNumber());
                            address.setFaxNumber(adresseDTO.getFaxNumber());
                            address.setMerchant(newMerchant);
                            return address;
                        }).collect(Collectors.toList()) : new ArrayList<>();

        newMerchant.setAddresses(addressesList);

        List<activities> activitiesList = (merchantDTO.getActivities() != null) ?
                merchantDTO.getActivities().stream()
                        .map(activitiesDTO -> {
                            activities activity = new activities();
                            activity.setActivityName(activitiesDTO.getActivityName());
                            activity.setMerchant(newMerchant);
                            return activity;
                        }).collect(Collectors.toList()) : new ArrayList<>();

        newMerchant.setActivities(activitiesList);

        // Set all fields from DTO
        newMerchant.setMerchantNumber(merchantDTO.getMerchantNumber());
        newMerchant.setMerchantName(merchantDTO.getMerchantName());
        newMerchant.setStatus(merchantDTO.getStatus());
        newMerchant.setTaxRate(merchantDTO.getTaxRate());
        newMerchant.setContactInfo(merchantDTO.getContactInfo());
        newMerchant.setBankAccountDetails(merchantDTO.getBankAccountDetails());
        newMerchant.setContractStatus(merchantDTO.getContractStatus());
        newMerchant.setSettlementOption(merchantDTO.getSettlementOption());
        newMerchant.setFeeStructure(merchantDTO.getFeeStructure());

        newMerchant.setUpdated_at(new Date(System.currentTimeMillis()));
        newMerchant.setDeleted_at(null);
        newMerchant.setDeleted_by(null);
        newMerchant.setCreated_by(new Date(System.currentTimeMillis()));
        newMerchant.setUpdated_by(null);

        return merchantRepository.save(newMerchant);
//        kafkaProducerService.sendEvent("ContractCreationTopic", new MerchantCreatedEvent(merchantDTO));
    }

    public Merchant updateMerchant(long id, MerchantDTO merchantDTO) {
        Optional<Merchant> existingMerchantOpt = merchantRepository.findById(id);
        if (existingMerchantOpt.isPresent()) {
            Merchant existingMerchant = existingMerchantOpt.get();
            merchantMapper.updateEntityFromDto(merchantDTO, existingMerchant);

            // Ensure addresses and activities are not null
            List<addresses> addressesList = (merchantDTO.getAddresses() != null) ?
                    merchantDTO.getAddresses().stream()
                            .map(adresseDTO -> {
                                addresses address = new addresses();
                                address.setStreet(adresseDTO.getStreet());
                                address.setCity(adresseDTO.getCity());
                                address.setState(adresseDTO.getState());
                                address.setCountry(adresseDTO.getCountry());
                                address.setZipCode(adresseDTO.getZipCode());
                                address.setEmail(adresseDTO.getEmail());
                                address.setPhoneNumber(adresseDTO.getPhoneNumber());
                                address.setFaxNumber(adresseDTO.getFaxNumber());
                                address.setMerchant(existingMerchant);
                                return address;
                            }).collect(Collectors.toList()) : new ArrayList<>();

            existingMerchant.getAddresses().clear();
            existingMerchant.getAddresses().addAll(addressesList);

            List<activities> activitiesList = (merchantDTO.getActivities() != null) ?
                    merchantDTO.getActivities().stream()
                            .map(activitiesDTO -> {
                                activities activity = new activities();
                                activity.setActivityName(activitiesDTO.getActivityName());
                                activity.setMerchant(existingMerchant);
                                return activity;
                            }).collect(Collectors.toList()) : new ArrayList<>();

            existingMerchant.getActivities().clear();
            existingMerchant.getActivities().addAll(activitiesList);

            // Update all fields
            existingMerchant.setMerchantNumber(merchantDTO.getMerchantNumber());
            existingMerchant.setMerchantName(merchantDTO.getMerchantName());
            existingMerchant.setStatus(merchantDTO.getStatus());
            existingMerchant.setTaxRate(merchantDTO.getTaxRate());
            existingMerchant.setContactInfo(merchantDTO.getContactInfo());
            existingMerchant.setBankAccountDetails(merchantDTO.getBankAccountDetails());
            existingMerchant.setContractStatus(merchantDTO.getContractStatus());
            existingMerchant.setSettlementOption(merchantDTO.getSettlementOption());
            existingMerchant.setFeeStructure(merchantDTO.getFeeStructure());

            // Update timestamp fields if needed
            existingMerchant.setUpdated_at(new Date(System.currentTimeMillis()));

            Merchant updatedMerchant = merchantRepository.save(existingMerchant);
            return updatedMerchant;
        } else {
            throw new RuntimeException("Merchant not found");
        }
//            kafkaProducerService.sendEvent("ContractCreationTopic", new MerchantUpdatedEvent(merchantDTO));
    }

    public void deleteMerchant(long id) {
        merchantRepository.deleteById(id);
//        kafkaProducerService.sendEvent("ContractCreationTopic", new MerchantDeletedEvent(id));
    }


    // Merchant Servicing Update To Merchant
    public Merchant updateMerchantMSS(long id, MerchantDTO merchantDTO) {
        Optional<Merchant> existingMerchantOpt = merchantRepository.findById(id);
        if (existingMerchantOpt.isPresent()) {
            Merchant existingMerchant = existingMerchantOpt.get();
            merchantMapper.updateEntityFromDto(merchantDTO, existingMerchant);

            // Ensure addresses is not null
            List<addresses> addressesList = (merchantDTO.getAddresses() != null) ?
                    merchantDTO.getAddresses().stream()
                            .map(adresseDTO -> {
                                addresses address = new addresses();
                                address.setStreet(adresseDTO.getStreet());
                                address.setCity(adresseDTO.getCity());
                                address.setState(adresseDTO.getState());
                                address.setCountry(adresseDTO.getCountry());
                                address.setZipCode(adresseDTO.getZipCode());
                                address.setEmail(adresseDTO.getEmail());
                                address.setPhoneNumber(adresseDTO.getPhoneNumber());
                                address.setFaxNumber(adresseDTO.getFaxNumber());
                                address.setMerchant(existingMerchant);
                                return address;
                            }).collect(Collectors.toList()) : new ArrayList<>();

            // Clear existing addresses and add new ones
            existingMerchant.getAddresses().clear();
            existingMerchant.getAddresses().addAll(addressesList);

            // Ensure activities is not null
            List<activities> activitiesList = (merchantDTO.getActivities() != null) ?
                    merchantDTO.getActivities().stream()
                            .map(activitiesDTO -> {
                                activities activity = new activities();
                                activity.setActivityName(activitiesDTO.getActivityName());
                                activity.setMerchant(existingMerchant);
                                return activity;
                            }).collect(Collectors.toList()) : new ArrayList<>();

            // Clear existing activities and add new ones
            existingMerchant.getActivities().clear();
            existingMerchant.getActivities().addAll(activitiesList);

            return merchantRepository.save(existingMerchant);
        } else {
            throw new RuntimeException("Merchant not found");
        }
    }









    public Contract createContract(ContractDTO contractDTO) {
        Contract newContract = new Contract(
                contractDTO.getMerchantID(),
                contractDTO.getContractID(),
                contractDTO.getMerchantDDA(),
                contractDTO.getContractStarts(),
                contractDTO.getContractEnds(),
                contractDTO.getSettlementOption(),
                contractDTO.getFeeStructure()
        );

        Contract savedContract = contractRepository.save(newContract);
//        kafkaProducerService.sendEvent("ContractCreationTopic", new ContractCreatedEvent(contractDTO));
        return savedContract;
    }

    public Contract updateContract(long id, ContractDTO contractDTO) {
        Optional<Contract> existingContractOpt = contractRepository.findById(id);
        if (existingContractOpt.isPresent()) {
            Contract existingContract = existingContractOpt.get();
            existingContract.setMerchantID(contractDTO.getMerchantID());
            existingContract.setContractID(contractDTO.getContractID());
            existingContract.setMerchantDDA(contractDTO.getMerchantDDA());
            existingContract.setContractStarts(contractDTO.getContractStarts());
            existingContract.setContractEnds(contractDTO.getContractEnds());
            existingContract.setSettlementOption(contractDTO.getSettlementOption());
            existingContract.setFeeStructure(contractDTO.getFeeStructure());

            Contract updatedContract = contractRepository.save(existingContract);
//            kafkaProducerService.sendEvent("ContractCreationTopic", new ContractUpdatedEvent(contractDTO));
            return updatedContract;
        } else {
            throw new RuntimeException("Contract not found");
        }
    }

    public void deleteContract(long id) {
        contractRepository.deleteById(id);
//        kafkaProducerService.sendEvent("ContractCreationTopic", new ContractDeletedEvent(id));
    }

}
