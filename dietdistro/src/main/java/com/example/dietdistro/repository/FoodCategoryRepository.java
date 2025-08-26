package com.example.dietdistro.repository;

import com.example.dietdistro.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    Optional<FoodCategory> findByName(String foodName);
    Boolean existsByName(String foodName);
}
