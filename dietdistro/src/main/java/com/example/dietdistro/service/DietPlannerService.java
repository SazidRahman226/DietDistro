package com.example.dietdistro.service;

import com.example.dietdistro.model.FoodCategory;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.repository.FoodCategoryRepository;
import com.example.dietdistro.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DietPlannerService {

    private final FoodItemRepository foodItemRepository;
    private final FoodCategoryRepository foodCategoryRepository;

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
}
