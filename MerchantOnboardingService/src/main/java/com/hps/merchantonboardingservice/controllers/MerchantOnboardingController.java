package com.hps.merchantonboardingservice.controllers;
import com.hps.merchantonboardingservice.dto.ContractDTO;
import com.hps.merchantonboardingservice.dto.MerchantDTO;

import com.hps.merchantonboardingservice.entities.Contract;
import com.hps.merchantonboardingservice.entities.Merchant;
import com.hps.merchantonboardingservice.services.MerchantOnboardingService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/merchants/onboarding")
public class MerchantOnboardingController {
    @Autowired
    private MerchantOnboardingService merchantService;
    @Autowired
    private KafkaAdmin kafkaAdmin;
    @Autowired
    private Map<String, Object> adminConfigs;


    @PostConstruct
    public void createTopic() {
        String topicName = "merchant-onboarding";
        int numPartitions = 1;
        short replicationFactor = 1;

        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(newTopic);
    }


    @PostMapping("/contracts")
    public ResponseEntity<Contract> createContract(@RequestBody ContractDTO contractDTO) {
        Contract createdContract = merchantService.createContract(contractDTO);
        return ResponseEntity.created(URI.create("/contracts/" + createdContract.getContractID())).body(createdContract);
    }


    @PostMapping("/merchants")
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        Merchant createdMerchant = merchantService.createMerchant(merchantDTO);
        return ResponseEntity.created(URI.create("/merchants/" + createdMerchant.getMerchantId())).body(createdMerchant);
    }



    @PutMapping("/contractUpdated/{id}")
    public Contract updateContract(@PathVariable long id, @RequestBody ContractDTO contractDTO) {
        return merchantService.updateContract(id, contractDTO);
    }


    @PutMapping("/merchantUpdated/{id}")
    public Merchant updateMerchant(@PathVariable long id, @RequestBody MerchantDTO merchantDTO) {
        return merchantService.updateMerchant(id, merchantDTO);
    }


    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        merchantService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/merchants/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }


}
