package com.hps.merchantonboardingservice.controllers;
import com.hps.merchantonboardingservice.dto.ActivitiesDTO;
import com.hps.merchantonboardingservice.dto.AdresseDTO;
import com.hps.merchantonboardingservice.dto.ContractDTO;
import com.hps.merchantonboardingservice.dto.MerchantDTO;

import com.hps.merchantonboardingservice.entities.Contract;
import com.hps.merchantonboardingservice.entities.Merchant;
import com.hps.merchantonboardingservice.mapper.ContractMapper;
import com.hps.merchantonboardingservice.mapper.MerchantMapper;
import com.hps.merchantonboardingservice.repos.ContractRepo;
import com.hps.merchantonboardingservice.repos.MerchantRepo;
import com.hps.merchantonboardingservice.services.MerchantOnboardingService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/merchants/onboarding")
public class MerchantOnboardingController {
    @Autowired
    private MerchantOnboardingService merchantService;
    @Autowired
    MerchantRepo merchantRepository;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    ContractRepo contractRepo;
    @Autowired
    private ContractMapper contractMapper;

//    @Autowired
//    private KafkaAdmin kafkaAdmin;
//    @Autowired
//    private Map<String, Object> adminConfigs;
//
//
//    @PostConstruct
//    public void createTopic() {
//        String topicName = "transactionControle-merchant-topic";
//        int numPartitions = 1;
//        short replicationFactor = 3;
//
//        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
//        kafkaAdmin.createOrModifyTopics(newTopic);
//    }
    //    @PostConstruct
//    public void createTopic() {
//        String topicName = "settelment-merchant-topic";
//        int numPartitions = 1;
//        short replicationFactor = 1;
//
//        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
//        kafkaAdmin.createOrModifyTopics(newTopic);
//    }


    @PostMapping("/contracts")
    public ResponseEntity<Contract> createContract(@RequestBody ContractDTO contractDTO) {
        Contract createdContract = merchantService.createContract(contractDTO);
        return ResponseEntity.created(URI.create("/contracts/" + createdContract.getContractID())).body(createdContract);
    }


    @PostMapping("/merchants")
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        Merchant createdMerchant = merchantService.createMerchant(merchantDTO);
        return ResponseEntity.created(URI.create("/merchants/" + createdMerchant.getMerchantNumber())).body(createdMerchant);
    }

    @GetMapping("/allMerchants")
    public List<MerchantDTO> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();

        // Check if the list is empty
        if (merchants.isEmpty()) {
            return Collections.emptyList(); // or return a suitable response
        }

        // Convert to DTOs manually
        return merchants.stream()
                .map(merchant -> new MerchantDTO(
                        merchant.getId(),
                        merchant.getMerchantNumber(),
                        merchant.getMerchantName(),
                        merchant.getStatus(),
                        merchant.getTaxRate(),
                        merchant.getAccountBalance(),
                        merchant.getBankAccountDetails(),
                        merchant.getContractStatus(),
                        merchant.getCreated_at(),
                        merchant.getUpdated_at(),
                        merchant.getDeleted_at(),
                        merchant.getDeleted_by(),
                        merchant.getCreated_by(),
                        merchant.getUpdated_by(),
                        merchant.getSettlementOption(),
                        merchant.getFeeStructure(),
                        // Manually convert addresses and activities
                        merchant.getAddresses().stream()
                                .filter(address -> address.getIsPrimary()) // Filter for primary addresses
                                .map(address -> new AdresseDTO(
                                        address.getAdresseId(),
                                        address.getIsPrimary(),
                                        address.getStreet(),
                                        address.getCity(),
                                        address.getState(),
                                        address.getCountry(),
                                        address.getZipCode(),
                                        address.getEmail(),
                                        address.getPhoneNumber(),
                                        address.getFaxNumber()
                                ))
                                .collect(Collectors.toList()),
                        merchant.getActivities().stream()
                                .map(activity -> new ActivitiesDTO(
                                        activity.getActivityId(),
                                        activity.getActivityName()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable Long id) {
        Optional<Merchant> merchantOptional = merchantRepository.findById(id);

        if (merchantOptional.isPresent()) {
            Merchant merchant = merchantOptional.get();

            MerchantDTO merchantDTO = new MerchantDTO(
                    merchant.getId(),
                    merchant.getMerchantNumber(),
                    merchant.getMerchantName(),
                    merchant.getStatus(),
                    merchant.getTaxRate(),
                    merchant.getAccountBalance(),
                    merchant.getBankAccountDetails(),
                    merchant.getContractStatus(),
                    merchant.getCreated_at(),
                    merchant.getUpdated_at(),
                    merchant.getDeleted_at(),
                    merchant.getDeleted_by(),
                    merchant.getCreated_by(),
                    merchant.getUpdated_by(),
                    merchant.getSettlementOption(),
                    merchant.getFeeStructure(),
                    merchant.getAddresses().stream()
                            .map(address -> new AdresseDTO(
                                    address.getAdresseId(),
                                    address.getIsPrimary(),
                                    address.getStreet(),
                                    address.getCity(),
                                    address.getState(),
                                    address.getCountry(),
                                    address.getZipCode(),
                                    address.getEmail(),
                                    address.getPhoneNumber(),
                                    address.getFaxNumber()
                            ))
                            .collect(Collectors.toList()),
                    merchant.getActivities().stream()
                            .map(activity -> new ActivitiesDTO(
                                    activity.getActivityId(),
                                    activity.getActivityName()
                            ))
                            .collect(Collectors.toList())
            );

            return ResponseEntity.ok(merchantDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @GetMapping("/merchant/{merchantNumber}")
    public ResponseEntity<MerchantDTO> getMerchantByMerchantNumber(@PathVariable String merchantNumber) {
        Optional<Merchant> merchantOptional = merchantRepository.findByMerchantNumber(merchantNumber);
        if (merchantOptional.isPresent()) {
            Merchant merchant = merchantOptional.get();

            MerchantDTO merchantDTO = new MerchantDTO(
                    merchant.getId(),
                    merchant.getMerchantNumber(),
                    merchant.getMerchantName(),
                    merchant.getStatus(),
                    merchant.getTaxRate(),
                    merchant.getAccountBalance(),
                    merchant.getBankAccountDetails(),
                    merchant.getContractStatus(),
                    merchant.getCreated_at(),
                    merchant.getUpdated_at(),
                    merchant.getDeleted_at(),
                    merchant.getDeleted_by(),
                    merchant.getCreated_by(),
                    merchant.getUpdated_by(),
                    merchant.getSettlementOption(),
                    merchant.getFeeStructure(),
                    merchant.getAddresses().stream()
                            .map(address -> new AdresseDTO(
                                    address.getAdresseId(),
                                    address.getIsPrimary(),
                                    address.getStreet(),
                                    address.getCity(),
                                    address.getState(),
                                    address.getCountry(),
                                    address.getZipCode(),
                                    address.getEmail(),
                                    address.getPhoneNumber(),
                                    address.getFaxNumber()
                            ))
                            .collect(Collectors.toList()),
                    merchant.getActivities().stream()
                            .map(activity -> new ActivitiesDTO(
                                    activity.getActivityId(),
                                    activity.getActivityName()
                            ))
                            .collect(Collectors.toList())
            );

            return ResponseEntity.ok(merchantDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @GetMapping("/contract/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long id) {
        Optional<Contract> contractOpt = contractRepo.findById(id);
        if (contractOpt.isPresent()) {
            Contract contract = contractOpt.get();
            ContractDTO contractDTO = new ContractDTO(
                    contract.getContractID(),
                    contract.getMerchantNumber(),
                    contract.getMerchantDDA(),
                    contract.getContractStarts(),
                    contract.getContractEnds(),
                    contract.getSettlementOption(),
                    contract.getFeeStructure()
            );
            return ResponseEntity.ok(contractDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allContracts")
    public List<ContractDTO> getAllContracts() {
        List<Contract> contracts = contractRepo.findAll();
        if (contracts.isEmpty()) {
            return Collections.emptyList();
        }
        return contracts.stream()
                .map(contract -> new ContractDTO(
                        contract.getContractID(),
                        contract.getMerchantNumber(),
                        contract.getMerchantDDA(),
                        contract.getContractStarts(),
                        contract.getContractEnds(),
                        contract.getSettlementOption(),
                        contract.getFeeStructure()
                ))
                .collect(Collectors.toList());
    }

    @PutMapping("/contractUpdated/{id}")
    public Contract updateContract(@PathVariable long id, @RequestBody ContractDTO contractDTO) {
        return merchantService.updateContract(id, contractDTO);
    }


    @PutMapping("/merchantUpdated/{id}")
    public Merchant updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) {
        return merchantService.updateMerchant(id, merchantDTO);
    }

    @PutMapping("/merchantUpdatedMSS/{id}")
    public Merchant updateMerchantMSS(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) {
        return merchantService.updateMerchantMSS(id, merchantDTO);
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

    @GetMapping("/merchantNumbers")
    public ResponseEntity<List<String>> getAllMerchantNumbers() {
        List<String> merchantNumbers = merchantService.getAllMerchantNumbers();
        return new ResponseEntity<>(merchantNumbers, HttpStatus.OK);
    }

}
