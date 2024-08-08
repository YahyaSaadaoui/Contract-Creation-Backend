package com.hps.merchantservicingservice.Repo;

import com.hps.merchantservicingservice.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
