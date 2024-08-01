package com.hps.merchantservicingservice.feign;


import com.hps.merchantservicingservice.dto.MerchantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@FeignClient("MERCHANTONBOARDINGSERVICE")
public interface MerchantInterface {

    @PostMapping("/api/merchants/onboarding")
    ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO);


    @GetMapping("/api/merchant/{id}")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable Long id) throws Exception ;



    @PutMapping("/api/merchant/{id}")
    public ResponseEntity<MerchantDTO> updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) throws Exception ;



    @DeleteMapping("/api/merchant/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) ;
}
