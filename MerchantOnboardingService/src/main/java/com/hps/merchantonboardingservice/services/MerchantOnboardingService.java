package com.hps.merchantonboardingservice.services;

import com.hps.merchantonboardingservice.dto.MerchantDTO;
import com.hps.merchantonboardingservice.entities.Merchant;
import com.hps.merchantonboardingservice.mapper.MerchantMapper;
import com.hps.merchantonboardingservice.repos.MerchantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantOnboardingService {
    private final MerchantRepo merchantRepository;
    private final MerchantMapper merchantMapper;
    private static final Logger logger = LoggerFactory.getLogger(MerchantOnboardingService.class);

    @Autowired
    public MerchantOnboardingService(MerchantRepo merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        logger.info("Received MerchantDTO: {}", merchantDTO);
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        Merchant savedMerchant = merchantRepository.save(merchant);
        MerchantDTO savedMerchantDTO = merchantMapper.toDto(savedMerchant);
        logger.info("Saved MerchantDTO: {}", savedMerchantDTO);
        return savedMerchantDTO;
    }

    public MerchantDTO getMerchantById(Long id) throws Exception {
        Optional<Merchant> merchantOptional = merchantRepository.findById(id);
        if (merchantOptional.isPresent()) {
            return merchantMapper.toDto(merchantOptional.get());
        } else {
            throw new Exception("Merchant not found with id: " + id);
        }
    }

    public MerchantDTO updateMerchant(Long id, MerchantDTO merchantDTO) throws Exception {
        Merchant existingMerchant = merchantRepository.findById(id).orElseThrow(() -> new Exception("Merchant not found with id: " + id));
        merchantMapper.updateEntityFromDto(merchantDTO, existingMerchant);
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return merchantMapper.toDto(updatedMerchant);
    }

    public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }
}
