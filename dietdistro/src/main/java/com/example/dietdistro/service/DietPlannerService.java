package com.example.dietdistro.service;

import com.example.dietdistro.dto.FoodItemBatchRequest;
import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.dto.MealDto;
import com.example.dietdistro.dto.MenuRequest;
import com.example.dietdistro.dto.MenuRequest;
import com.example.dietdistro.model.*;
import com.example.dietdistro.repository.FoodCategoryRepository;
import com.example.dietdistro.repository.FoodItemRepository;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                .orElseThrow(() -> new IllegalArgumentException("Category 'Protein' not found"));

        return foodItemRepository.findByCategories(proteinCategory);
    }

    public List<FoodItem> getFatFood()
    {
        FoodCategory fatCategory = foodCategoryRepository.findByName("Fat")
                .orElseThrow(() -> new IllegalArgumentException("Category 'Fat' not found"));

        return foodItemRepository.findByCategories(fatCategory);
    }

    public void saveFoods(User user, MenuRequest menuRequest) {

        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Menu menu = menuRepository.findById(menuRequest.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));

        menu.setBreakfast(menuRequest.getBreakfast());
        menu.setLunch(menuRequest.getLunch());
        menu.setDinner(menuRequest.getDinner());

        menu.setTotalCalorie(calculateMenuCalorie(menu));

        healthProfile.getMenuIds().add(menuRequest.getMenuId());
        menuRepository.save(menu);
        healthProfileRepository.save(healthProfile);

    }

    private void setMeal(Meal meal, MealDto mealDto)
    {
        meal.setCarbFood(mealDto.getCarbFood());
        meal.setProteinFood(mealDto.getProteinFood());
        meal.setFatFood(mealDto.getFatFood());
    }

    private double calculateMealCalorie(Meal meal)
    {
        double totalCal = 0.0;

        for(FoodEntry food : meal.getCarbFood())
        {
            totalCal += foodItemRepository.findById(food.getFoodId()).orElseThrow().getCalorie() * (food.getQuantity()/100.0);
        }

        for(FoodEntry food : meal.getProteinFood())
        {
            totalCal += foodItemRepository.findById(food.getFoodId()).orElseThrow().getCalorie() * (food.getQuantity()/100.0);
        }

        for(FoodEntry food : meal.getFatFood())
        {
            totalCal += foodItemRepository.findById(food.getFoodId()).orElseThrow().getCalorie() * (food.getQuantity()/100.0);
        }

        return totalCal;
    }

    private double calculateMenuCalorie(Menu menu)
    {
        return calculateMealCalorie(menu.getBreakfast()) + calculateMealCalorie(menu.getLunch()) + calculateMealCalorie(menu.getDinner());
    }



//    public Boolean saveProteinFood(User user, MenuAddFood menuAddFood)
//    {
//        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//
//        Menu menu = menuRepository.findById(menuAddFood.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));
//
//        menu.getProteinFood().addAll(menuAddFood.getFoodIds());
//        healthProfile.getMenuIds().add(menuAddFood.getMenuId());
//        menuRepository.save(menu);
//        healthProfileRepository.save(healthProfile);
//
//        return isOverLimit(menu, menuAddFood);
//    }
//
//    public Boolean saveFatFood(User user, MenuAddFood menuAddFood)
//    {
//        HealthProfile healthProfile = healthProfileRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//
//        Menu menu = menuRepository.findById(menuAddFood.getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found!"));
//
//        menu.getFatFood().addAll(menuAddFood.getFoodIds());
//        healthProfile.getMenuIds().add(menuAddFood.getMenuId());
//        menuRepository.save(menu);
//        healthProfileRepository.save(healthProfile);
//
//        return isOverLimit(menu, menuAddFood);
//    }
//
}
