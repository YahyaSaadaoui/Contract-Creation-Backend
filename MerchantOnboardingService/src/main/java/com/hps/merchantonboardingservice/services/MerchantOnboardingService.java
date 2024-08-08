package com.hps.merchantonboardingservice.services;

import com.hps.merchantonboardingservice.dto.ContractDTO;
import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Contract;
import com.hps.merchantonboardingservice.entities.Merchant;
import com.hps.merchantonboardingservice.events.*;
import com.hps.merchantonboardingservice.mapper.ContractMapper;
import com.hps.merchantonboardingservice.mapper.MerchantMapper;
import com.hps.merchantonboardingservice.repos.ContractRepo;
import com.hps.merchantonboardingservice.repos.MerchantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantOnboardingService {

    @Autowired
    private MerchantRepo merchantRepository;

    @Autowired
    private ContractRepo contractRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Merchant createMerchant(MerchantDTO merchantDTO) {
        Merchant newMerchant = new Merchant(
                merchantDTO.getMerchantId(),
                merchantDTO.getMerchantNumber(),
                merchantDTO.getMerchantName(),
                merchantDTO.getContactInfo(),
                merchantDTO.getBankAccountDetails(),
                merchantDTO.getContractStatus(),
                merchantDTO.getUpdated_at(),
                merchantDTO.getDeleted_at(),
                merchantDTO.getDeleted_by(),
                merchantDTO.getCreated_by(),
                merchantDTO.getUpdated_by(),
                merchantDTO.getSettlementOption(),
                merchantDTO.getFeeStructure()
        );

        Merchant savedMerchant = merchantRepository.save(newMerchant);
        kafkaProducerService.sendMerchantCreatedEvent(new MerchantCreatedEvent(merchantDTO));
        return savedMerchant;
    }

    public Merchant updateMerchant(long id, MerchantDTO merchantDTO) {
        Optional<Merchant> existingMerchantOpt = merchantRepository.findById(id);
        if (existingMerchantOpt.isPresent()) {
            Merchant existingMerchant = existingMerchantOpt.get();
            existingMerchant.setMerchantNumber(merchantDTO.getMerchantNumber());
            existingMerchant.setMerchantName(merchantDTO.getMerchantName());
            existingMerchant.setContactInfo(merchantDTO.getContactInfo());
            existingMerchant.setBankAccountDetails(merchantDTO.getBankAccountDetails());
            existingMerchant.setContractStatus(merchantDTO.getContractStatus());
            existingMerchant.setUpdated_at(merchantDTO.getUpdated_at());
            existingMerchant.setDeleted_at(merchantDTO.getDeleted_at());
            existingMerchant.setDeleted_by(merchantDTO.getDeleted_by());
            existingMerchant.setCreated_by(merchantDTO.getCreated_by());
            existingMerchant.setUpdated_by(merchantDTO.getUpdated_by());
            existingMerchant.setSettlementOption(merchantDTO.getSettlementOption());
            existingMerchant.setFeeStructure(merchantDTO.getFeeStructure());

            Merchant updatedMerchant = merchantRepository.save(existingMerchant);
            kafkaProducerService.sendMerchantUpdatedEvent(new MerchantUpdatedEvent(merchantDTO));
            return updatedMerchant;
        } else {
            throw new RuntimeException("Merchant not found");
        }
    }

    public void deleteMerchant(long id) {
        merchantRepository.deleteById(id);
        kafkaProducerService.sendMerchantDeletedEvent(new MerchantDeletedEvent(id));
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
        kafkaProducerService.sendContractCreatedEvent(new ContractCreatedEvent(contractDTO));
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
            kafkaProducerService.sendContractUpdatedEvent(new ContractUpdatedEvent(contractDTO));
            return updatedContract;
        } else {
            throw new RuntimeException("Contract not found");
        }
    }

    public void deleteContract(long id) {
        contractRepository.deleteById(id);
        kafkaProducerService.sendContractDeletedEvent(new ContractDeletedEvent(id));
    }

    @KafkaListener(topics = {"merchantCreated", "merchantUpdated", "merchantDeleted"}, groupId = "merchant-servcing-service")
    public void listenMerchantServicingServiceEvents(Object event) {
        if (event instanceof MerchantUpdatedEvent) {
            MerchantUpdatedEvent merchantUpdatedEvent = (MerchantUpdatedEvent) event;
            this.updateMerchant(merchantUpdatedEvent.getMerchantDTO().getMerchantId(), merchantUpdatedEvent.getMerchantDTO());
        } else if (event instanceof MerchantDeletedEvent) {
            MerchantDeletedEvent merchantDeletedEvent = (MerchantDeletedEvent) event;
            this.deleteMerchant(merchantDeletedEvent.getMerchantId());
        }
    }
}
