package com.hps.merchantonboardingservice.repos;


import com.hps.merchantonboardingservice.entities.addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresseRepo extends JpaRepository<addresses, Long> {
}
