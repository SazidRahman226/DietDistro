package com.example.dietdistro.service;

import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepo;

    public FoodItem saveOrUpdateFoodItem(FoodItemRequest itemRequest)
    {
        FoodItem _food = foodItemRepo.findByFoodName(itemRequest.getFoodName())
                .orElse(new FoodItem());
        _food.setFoodName(itemRequest.getFoodName());
        _food.setDescription(itemRequest.getDescription());
        _food.setCategories(itemRequest.getFoodCategories());
        _food.setCalorie(itemRequest.getCalorie());
        _food.setCarbohydrate(itemRequest.getCarbohydrate());
        _food.setProtein((itemRequest.getProtein()));
        _food.setFat(itemRequest.getFat());
        return foodItemRepo.save(_food);
    }

    public Optional<FoodItem> getInfo(String foodName)
    {
        return foodItemRepo.findByFoodName(foodName);
    }
}
