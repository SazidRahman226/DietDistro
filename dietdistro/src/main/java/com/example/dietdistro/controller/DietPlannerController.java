package com.example.dietdistro.controller;

import com.example.dietdistro.dto.FoodItemBatchRequest;
import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.dto.MenuAddFood;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.Menu;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.DietPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DietPlannerController {

    private final DietPlannerService dietPlannerService;
    private final MenuRepository menuRepository;
    private final HealthProfileRepository healthProfileRepository;

    @PostMapping("/create-menu")
    public ResponseEntity<?> createMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        Menu menu = new Menu();
        menuRepository.save(menu);
        HealthProfile healthProfile = healthProfileRepository.findByUser(customUserDetails.getUser()).orElseThrow(() -> new RuntimeException("User not found!"));
        healthProfile.getMenuIds().add(menu.getId());
        healthProfileRepository.save(healthProfile);
        return ResponseEntity.ok(menu.getId());
    }

    @GetMapping("/create-menu/carbohydrate")
    public ResponseEntity<?> getCarbohydrate(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> carbFoods = dietPlannerService.getCarbFood();
        return ResponseEntity.ok(carbFoods);
    }

    @PostMapping("/create-menu/carbohydrate")
    public ResponseEntity<?> setCarbohydrate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MenuAddFood menuAddFood)
    {
        dietPlannerService.saveCarbFood(customUserDetails.getUser(), menuAddFood);
        return ResponseEntity.ok("Carbohydrate added!");
    }

    @GetMapping("/create-menu/protein")
    public ResponseEntity<?> getProtein(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> proteinFoods = dietPlannerService.getProteinFood();
        return ResponseEntity.ok(proteinFoods);
    }

    @PostMapping("/create-menu/protein")
    public ResponseEntity<?> setProtein(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MenuAddFood menuAddFood)
    {

        dietPlannerService.saveProteinFood(customUserDetails.getUser(), menuAddFood);
        return ResponseEntity.ok("Protein added!");
    }

    @GetMapping("/create-menu/fat")
    public ResponseEntity<?> getFat(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> fatFoods = dietPlannerService.getFatFood();
        return ResponseEntity.ok(fatFoods);
    }

    @PostMapping("/create-menu/fat")
    public ResponseEntity<?> setFat(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MenuAddFood menuAddFood)
    {

        dietPlannerService.saveFatFood(customUserDetails.getUser(), menuAddFood);
        return ResponseEntity.ok("Fat added!");
    }

}
