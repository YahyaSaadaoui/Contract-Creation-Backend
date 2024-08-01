package com.hps.merchantservicingservice.controllers;


import com.hps.merchantservicingservice.Enums.FeeStructure;
import com.hps.merchantservicingservice.Enums.SettlementOption;
import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.services.MerchantServicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/merchants/servicing")
public class MerchanServicingController {

        @Autowired
        private MerchantServicingService merchantServicingService;

    @PostMapping("/create")
    public ResponseEntity<MerchantDTO> createMerchant() {
        // Create a MerchantDTO object with real data
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerchantId(1L);
        merchantDTO.setMerchantNumber("123456");
        merchantDTO.setMerchantName("Test Merchant");
        merchantDTO.setContactInfo("test@example.com");
        merchantDTO.setBankAccountDetails("1234-5678-9012-3456");
        merchantDTO.setContractStatus("Active");
        merchantDTO.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));
        merchantDTO.setCreated_by(new java.sql.Date(System.currentTimeMillis()));
        merchantDTO.setSettlementOption(SettlementOption.ON_DROP);
        merchantDTO.setFeeStructure(FeeStructure.STANDARD);

        MerchantDTO createdMerchant = merchantServicingService.createMerchant(merchantDTO);
        return ResponseEntity.ok(createdMerchant);
    }
}

