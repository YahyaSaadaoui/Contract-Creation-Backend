package com.hps.admindashboardservice.repos;

import com.hps.admindashboardservice.entities.Permission;
import com.hps.admindashboardservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface permissionRepository extends JpaRepository<Permission, Long> {
}
