package com.example.dietdistro.controller;

import com.example.dietdistro.dto.FoodItemBatchRequest;
import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.repository.FoodItemRepository;
import com.example.dietdistro.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info/food")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemServiceService;
    private final FoodItemRepository foodItemRepository;


    @PostMapping("/add-batch")
    public ResponseEntity<?> createOrUpdateProfile(@Valid @RequestBody FoodItemBatchRequest request) {

        for(FoodItemRequest foodItemRequest : request.getFoodItems()) {
            FoodItem foodITem = foodItemServiceService.saveOrUpdateFoodItem(foodItemRequest);
        }
        return ResponseEntity.ok("Items added successfully!");
    }

    @PostMapping("/add")
    public ResponseEntity<?> createOrUpdateProfile(@Valid @RequestBody FoodItemRequest foodItemRequest) {
        FoodItem foodITem = foodItemServiceService.saveOrUpdateFoodItem(foodItemRequest);
        return ResponseEntity.ok("Food item added!");

    }

    @GetMapping("/show")
    public ResponseEntity<?> getFoodWiki() {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        return ResponseEntity.ok(foodItems);
    }
}
