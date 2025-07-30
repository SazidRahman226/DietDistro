package com.example.dietdistro.repository;

import com.example.dietdistro.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByFoodName(String foodName);
}
