package com.hps.admindashboardservice.repos;

import com.hps.admindashboardservice.entities.CdfTypes;
import com.hps.admindashboardservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role, Long> {
}
