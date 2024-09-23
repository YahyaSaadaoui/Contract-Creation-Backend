package com.hps.merchantonboardingservice.repos;
import com.hps.merchantonboardingservice.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantNumber(String merchantNumber);
}
