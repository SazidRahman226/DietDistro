package com.example.dietdistro.service;

import com.example.dietdistro.dto.FoodItemBatchRequest;
import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.dto.MenuAddFood;
import com.example.dietdistro.model.*;
import com.example.dietdistro.repository.FoodCategoryRepository;
import com.example.dietdistro.repository.FoodItemRepository;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DietPlannerService {

    private final FoodItemRepository foodItemRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final HealthProfileRepository healthProfileRepository;
    private final MenuRepository menuRepository;

    public List<FoodItem> getCarbFood()
    {
        FoodCategory carbCategory = foodCategoryRepository.findByName("Carbohydrate")
                .orElseThrow(() -> new IllegalArgumentException("Category 'Carbohydrates' not found"));

        return foodItemRepository.findByCategories(carbCategory);
    }

    public List<FoodItem> getProteinFood()
    {
        FoodCategory proteinCategory = foodCategoryRepository.findByName("Protein")
                .orElseThrow(() -> new IllegalArgumentException("Category 'protein' not found"));

        return foodItemRepository.findByCategories(proteinCategory);
    }

    public List<FoodItem> getFatFood()
    {
        FoodCategory fatCategory = foodCategoryRepository.findByName("Fat")
                .orElseThrow(() -> new IllegalArgumentException("Category 'fat' not found"));

        return foodItemRepository.findByCategories(fatCategory);
    }

    public void saveCarbFood(User user, MenuAddFood menuAddFood) {

        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Menu menu = menuRepository.findById(menuAddFood.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));
        menu.getCarbFood().addAll(menuAddFood.getFoodIds());
        healthProfile.getMenuIds().add(menuAddFood.getMenuId());
        menuRepository.save(menu);
        healthProfileRepository.save(healthProfile);

    }

    public void saveProteinFood(User user, MenuAddFood menuAddFood)
    {
        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Menu menu = menuRepository.findById(menuAddFood.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));

        menu.getProteinFood().addAll(menuAddFood.getFoodIds());
        healthProfile.getMenuIds().add(menuAddFood.getMenuId());
        menuRepository.save(menu);
        healthProfileRepository.save(healthProfile);
    }

    public void saveFatFood(User user, MenuAddFood menuAddFood)
    {
        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Menu menu = menuRepository.findById(menuAddFood.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));

        menu.getFatFood().addAll(menuAddFood.getFoodIds());
        healthProfile.getMenuIds().add(menuAddFood.getMenuId());
        menuRepository.save(menu);
        healthProfileRepository.save(healthProfile);
    }
}
