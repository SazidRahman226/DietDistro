package com.example.dietdistro.controller;

import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.FoodItemRepository;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.FoodItemService;
import com.example.dietdistro.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/info/food")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemServiceService;
    private final FoodItemRepository foodItemRepository;


    @PostMapping("/add")
    public ResponseEntity<?> createOrUpdateProfile(String foodName,
            @Valid @RequestBody FoodItemRequest request) {
        FoodItem foodITem = foodItemServiceService.saveOrUpdateFoodItem(foodName, request);
        return ResponseEntity.ok("Food item added!");
    }

    @GetMapping("/show")
    public ResponseEntity<?> getFoodWiki() {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        return ResponseEntity.ok(foodItems);
    }
}
