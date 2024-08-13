package com.hps.merchantonboardingservice.repos;


import com.hps.merchantonboardingservice.entities.activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesRepo extends JpaRepository<activities, Long> {
}
