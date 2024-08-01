package com.hps.merchantservicingservice.services;

import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.feign.MerchantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MerchantServicingService {
    @Autowired
    MerchantInterface merchantInterface;
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        ResponseEntity<MerchantDTO> response = merchantInterface.createMerchant(merchantDTO);
        return response.getBody();
    }
}
