package com.hps.admindashboardservice.repos;

import com.hps.admindashboardservice.entities.FeePercentage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeePercentageRepository extends JpaRepository<FeePercentage, Long> {
}
