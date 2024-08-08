package com.hps.merchantservicingservice.services;

import com.hps.merchantservicingservice.Repo.MerchantRepository;
import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.entities.Merchant;
import com.hps.merchantservicingservice.events.MerchantDeletedEvent;
import com.hps.merchantservicingservice.events.MerchantUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantServicingService {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private KafkaProducerService kafkaProducerService;


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
    @KafkaListener(topics = {"merchantCreated", "merchantUpdated", "merchantDeleted"}, groupId = "merchant-onboarding")
    public void listenMerchantEvents(Object event) {
        if (event instanceof MerchantUpdatedEvent) {
            MerchantUpdatedEvent merchantUpdatedEvent = (MerchantUpdatedEvent) event;
            this.updateMerchant(merchantUpdatedEvent.getMerchantDTO().getMerchantId(), merchantUpdatedEvent.getMerchantDTO());
        } else if (event instanceof MerchantDeletedEvent) {
            MerchantDeletedEvent merchantDeletedEvent = (MerchantDeletedEvent) event;
            this.deleteMerchant(merchantDeletedEvent.getMerchantId());
        }
    }
}
