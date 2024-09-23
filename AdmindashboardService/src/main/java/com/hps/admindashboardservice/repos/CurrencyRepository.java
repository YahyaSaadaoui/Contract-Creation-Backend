package com.hps.admindashboardservice.repos;

import com.hps.admindashboardservice.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
