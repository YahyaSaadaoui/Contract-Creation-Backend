package com.hps.admindashboardservice.repos;

import com.hps.admindashboardservice.entities.settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface settingsRepo extends JpaRepository<settings, Long> {
}
