package com.example.dietdistro.service;

import com.example.dietdistro.model.FoodCategory;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.repository.FoodCategoryRepository;
import com.example.dietdistro.repository.FoodItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataLoaderService {

    @Value("${data.file.path}")
    private String dataFilePath; // Path to the external JSON file

    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodItemRepository foodItemRepository;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void loadData() throws IOException {
        // Load JSON data from the external file
        File file = new File(dataFilePath);
        DataJson dataJson = objectMapper.readValue(file, DataJson.class);

        // Load categories if not already in the repository
        for (FoodCategory category : dataJson.getCategories()) {
            if (!foodCategoryRepository.existsByName(category.getName())) {
                foodCategoryRepository.save(category);
            }
        }

        // Load food items
        for (DataJson.FoodItemData food : dataJson.getFoods()) {
            FoodItem foodItem;
            if(foodItemRepository.existsByFoodName(food.foodName))
                foodItem = foodItemRepository.findByFoodName(food.foodName).orElseThrow(() -> new RuntimeException("Food not Found!"));
            else
                foodItem = new FoodItem();
            foodItem.setFoodName(food.getFoodName());
            foodItem.setCalorie(food.getCalorie());
            foodItem.setCarbohydrate(food.getCarbohydrate());
            foodItem.setProtein(food.getProtein());
            foodItem.setFat(food.getFat());
            foodItem.setDescription(food.getDescription());

            // Fetch and set categories
            Set<FoodCategory> categories = food.getFoodCategories().stream()
                    .map(categoryName -> foodCategoryRepository.findByName(categoryName).orElseThrow())
                    .collect(Collectors.toSet());
            foodItem.setCategories(categories);

            // Save food item to the repository
            foodItemRepository.save(foodItem);
        }
    }

    // Inner class to represent the JSON structure
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataJson {
        // Getters and Setters
        private List<FoodCategory> categories;
        private List<FoodItemData> foods;

        // Inner class to represent the FoodItem data structure
        @Setter
        @Getter
        public static class FoodItemData {
            // Getters and Setters
            private String foodName;
            private List<String> foodCategories;
            private Double calorie;
            private Double carbohydrate;
            private Double protein;
            private Double fat;
            private String description;
        }
    }
}
