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
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
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
                            address.setIsPrimary(adresseDTO.getIsPrimary());
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
        newMerchant.setAccountBalance(merchantDTO.getAccountBalance());
        newMerchant.setBankAccountDetails(merchantDTO.getBankAccountDetails());
        newMerchant.setContractStatus(merchantDTO.getContractStatus());
        newMerchant.setSettlementOption(merchantDTO.getSettlementOption());
        newMerchant.setFeeStructure(merchantDTO.getFeeStructure());
        newMerchant.setCreated_at(new Date(System.currentTimeMillis()));
        newMerchant.setUpdated_at(null);
        newMerchant.setDeleted_at(null);
        newMerchant.setDeleted_by(merchantDTO.getCreated_by());
        newMerchant.setCreated_by(merchantDTO.getCreated_by());
        newMerchant.setUpdated_by(merchantDTO.getCreated_by());

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
                                address.setIsPrimary(adresseDTO.getIsPrimary());
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
            existingMerchant.setAccountBalance(merchantDTO.getAccountBalance());
            existingMerchant.setBankAccountDetails(merchantDTO.getBankAccountDetails());
            existingMerchant.setContractStatus(merchantDTO.getContractStatus());
            existingMerchant.setSettlementOption(merchantDTO.getSettlementOption());
            existingMerchant.setFeeStructure(merchantDTO.getFeeStructure());
            existingMerchant.setCreated_at(null);
            existingMerchant.setUpdated_at(new Date(System.currentTimeMillis()));
            existingMerchant.setDeleted_at(null);
            existingMerchant.setDeleted_by(null);
            existingMerchant.setCreated_by(null);
            existingMerchant.setUpdated_by(merchantDTO.getCreated_by());

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

            List<addresses> addressesList = (merchantDTO.getAddresses() != null) ?
                    merchantDTO.getAddresses().stream()
                            .map(adresseDTO -> {
                                addresses address = new addresses();
                                address.setIsPrimary(adresseDTO.getIsPrimary());
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
                                activity.setActivityId(activitiesDTO.getActivityId());
                                activity.setActivityName(activitiesDTO.getActivityName());
                                activity.setMerchant(existingMerchant);
                                return activity;
                            }).collect(Collectors.toList()) : new ArrayList<>();

            existingMerchant.getActivities().clear();
            existingMerchant.getActivities().addAll(activitiesList);

            return merchantRepository.save(existingMerchant);
        } else {
            throw new ResourceNotFoundException("Merchant with ID " + id + " not found");
        }
    }










    public Contract createContract(ContractDTO contractDTO) {
        Contract newContract = new Contract(
                contractDTO.getContractID(),
                contractDTO.getMerchantNumber(),
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
        Contract existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found"));;

            existingContract.setMerchantNumber(contractDTO.getMerchantNumber());
            existingContract.setMerchantDDA(contractDTO.getMerchantDDA());
            existingContract.setContractStarts(contractDTO.getContractStarts());
            existingContract.setContractEnds(contractDTO.getContractEnds());
            existingContract.setSettlementOption(contractDTO.getSettlementOption());
            existingContract.setFeeStructure(contractDTO.getFeeStructure());

        // kafkaProducerService.sendEvent("ContractCreationTopic", new ContractUpdatedEvent(contractDTO));
            return contractRepository.save(existingContract);
    }

    public void deleteContract(long id) {
        contractRepository.deleteById(id);
//        kafkaProducerService.sendEvent("ContractCreationTopic", new ContractDeletedEvent(id));
    }

    public List<String> getAllMerchantNumbers() {
        return merchantRepository.findAll()
                .stream()
                .map(Merchant::getMerchantNumber)
                .collect(Collectors.toList());
    }

}
