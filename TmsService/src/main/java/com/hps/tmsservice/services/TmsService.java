package com.hps.tmsservice.services;

import com.hps.tmsservice.dto.DeviceDTO;
import com.hps.tmsservice.dto.MerchantDTO;
import com.hps.tmsservice.entities.Device;
import com.hps.tmsservice.entities.Merchant;
import com.hps.tmsservice.events.*;
import com.hps.tmsservice.repos.DeviceRepo;
import com.hps.tmsservice.repos.MerchantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TmsService {

    @Autowired
    private MerchantRepo merchantRepository;

    @Autowired
    private DeviceRepo deviceRepo;

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


    public Device createDevice(DeviceDTO deviceDTO) {
        Device newDevice= new Device(
                deviceDTO.getDeviceNumber(),
                deviceDTO.getLocation(),
                deviceDTO.getStatus(),
                deviceDTO.getMerchantNumber()
        );

        Device savedDevice = deviceRepo.save(newDevice);
        kafkaProducerService.sendContractCreatedEvent(new DeviceCreatedEvent(deviceDTO));
        return savedDevice;
    }

    public Device updateContract(long id, DeviceDTO deviceDTO) {
        Optional<Device> existingDeviceOpt = deviceRepo.findById(id);
        if (existingDeviceOpt.isPresent()) {
            Device existingDevice = existingDeviceOpt.get();
            existingDevice.setDeviceNumber(deviceDTO.getDeviceNumber());
            existingDevice.setLocation(deviceDTO.getLocation());
            existingDevice.setStatus(deviceDTO.getStatus());
            existingDevice.setMerchantNumber( deviceDTO.getMerchantNumber());

            Device updatedContract = deviceRepo.save(existingDevice);
            kafkaProducerService.sendContractUpdatedEvent(new DeviceUpdatedEvent(deviceDTO));
            return updatedContract;
        } else {
            throw new RuntimeException("Device not found");
        }
    }

    public void deleteDevice(long id) {
        deviceRepo.deleteById(id);
        kafkaProducerService.sendContractDeletedEvent(new DeviceDeletedEvent(id));
    }
    public void deleteMerchant(long id) {
        merchantRepository.deleteById(id);
        kafkaProducerService.sendMerchantDeletedEvent(new MerchantDeletedEvent(id));
    }
    @KafkaListener(topics = {"merchantCreated", "merchantUpdated", "merchantDeleted"}, groupId = "merchant-onboarding")
    public void listenMerchantOnBoardingEvents(Object event) {
        if (event instanceof MerchantUpdatedEvent) {
            MerchantUpdatedEvent merchantUpdatedEvent = (MerchantUpdatedEvent) event;
            this.updateMerchant(merchantUpdatedEvent.getMerchantDTO().getMerchantId(), merchantUpdatedEvent.getMerchantDTO());
        } else if (event instanceof MerchantDeletedEvent) {
            MerchantDeletedEvent merchantDeletedEvent = (MerchantDeletedEvent) event;
            this.deleteMerchant(merchantDeletedEvent.getMerchantId());
        }
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
