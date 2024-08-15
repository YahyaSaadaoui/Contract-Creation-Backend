package com.hps.admindashboardservice.repos;
import com.hps.admindashboardservice.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<user, Long>{
    Optional<user> findByUsername(String username);
}
