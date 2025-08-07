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
        _food.setCategory(itemRequest.getCategory());
        _food.setDescription(itemRequest.getDescription());
        _food.setCaloriePerGram(itemRequest.getCaloriePerGram());
        return foodItemRepo.save(_food);
    }

    public Optional<FoodItem> getInfo(String foodName)
    {
        return foodItemRepo.findByFoodName(foodName);
    }
}
