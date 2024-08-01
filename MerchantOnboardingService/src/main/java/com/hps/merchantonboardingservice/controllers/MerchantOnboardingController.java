package com.hps.merchantonboardingservice.controllers;
import com.hps.merchantonboardingservice.dto.MerchantDTO;

import com.hps.merchantonboardingservice.services.MerchantOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/merchants/onboarding")
public class MerchantOnboardingController {
    @Autowired
    private MerchantOnboardingService merchantService;


    @PostMapping
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        MerchantDTO createdMerchant = merchantService.createMerchant(merchantDTO);
        return ResponseEntity.created(URI.create("/merchants/" + createdMerchant.getMerchantId())).body(createdMerchant);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable Long id) throws Exception {
        MerchantDTO merchantDTO = merchantService.getMerchantById(id);
        return ResponseEntity.ok(merchantDTO);
    }



    @PutMapping("/{id}")
    public ResponseEntity<MerchantDTO> updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) throws Exception {
        MerchantDTO updatedMerchant = merchantService.updateMerchant(id, merchantDTO);
        return ResponseEntity.ok(updatedMerchant);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
}
