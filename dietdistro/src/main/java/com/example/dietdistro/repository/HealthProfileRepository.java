package com.example.dietdistro.repository;

import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
    Optional<HealthProfile> findById(Long id);
}
