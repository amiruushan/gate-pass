package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByLocationName(String locationName);

    Optional<Location> findByLocationName(String locationName);

}