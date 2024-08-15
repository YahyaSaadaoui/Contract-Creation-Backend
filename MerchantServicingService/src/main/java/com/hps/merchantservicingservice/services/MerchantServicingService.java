package com.hps.merchantservicingservice.services;


import com.hps.merchantservicingservice.dto.ActivityDTO;
import com.hps.merchantservicingservice.dto.AdresseDTO;
import com.hps.merchantservicingservice.dto.MerchantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;


@Service
public class MerchantServicingService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String GATEWAY_URL = "http://localhost:8223/api/merchants/onboarding/merchantUpdated/";

    public void updateMerchant(Long id, MerchantDTO updateRequest) {
        String url = GATEWAY_URL + id;

        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(id);
        merchantDTO.setStatus(updateRequest.getStatus());
        merchantDTO.setTaxRate(updateRequest.getTaxRate());
        merchantDTO.setContactInfo(updateRequest.getContactInfo());
        merchantDTO.setBankAccountDetails(updateRequest.getBankAccountDetails());
        merchantDTO.setAddresses(updateRequest.getAddresses().stream()
                .map(addressDTO -> new AdresseDTO(
                        addressDTO.getStreet(),
                        addressDTO.getCity(),
                        addressDTO.getState(),
                        addressDTO.getCountry(),
                        addressDTO.getZipCode(),
                        addressDTO.getEmail(),
                        addressDTO.getPhoneNumber(),
                        addressDTO.getFaxNumber()
                )).collect(Collectors.toList()));
        merchantDTO.setActivities(updateRequest.getActivities().stream()
                .map(activityDTO -> new ActivityDTO(
                        activityDTO.getActivityName()
                )).collect(Collectors.toList()));

        restTemplate.put(url, merchantDTO);
    }

}
