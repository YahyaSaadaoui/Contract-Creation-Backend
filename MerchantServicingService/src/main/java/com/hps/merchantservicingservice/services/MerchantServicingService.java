package com.hps.merchantservicingservice.services;

import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.feign.MerchantInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MerchantServicingService {
    @Autowired
    MerchantInterface merchantInterface;
    private static final Logger logger = LoggerFactory.getLogger(MerchantServicingService.class);
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        logger.info("Sending MerchantDTO to MerchantOnboardingService: {}", merchantDTO);
        ResponseEntity<MerchantDTO> response = merchantInterface.createMerchant(merchantDTO);
        logger.info("Received response from MerchantOnboardingService: {}", response.getBody());
        return response.getBody();
    }
}
