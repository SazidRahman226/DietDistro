package com.example.dietdistro.controller;

import com.example.dietdistro.dto.FoodItemBatchRequest;
import com.example.dietdistro.dto.FoodItemRequest;
import com.example.dietdistro.dto.MenuRequest;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DietPlannerController {

    private final DietPlannerService dietPlannerService;
    private final MenuRepository menuRepository;
    private final HealthProfileRepository healthProfileRepository;

    @GetMapping("/create-menu")
    public ResponseEntity<?> createMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        Menu menu = new Menu();
        HealthProfile healthProfile = healthProfileRepository.findByUser(customUserDetails.getUser()).orElseThrow(() -> new RuntimeException("User not found!"));
        healthProfile.getMenuIds().add(menu.getId());
        menuRepository.save(menu);
        healthProfileRepository.save(healthProfile);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/create-menu/foods")
    public ResponseEntity<?> getFoods(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> carbFoods = dietPlannerService.getCarbFood();
        List<FoodItem> proteinFoods = dietPlannerService.getProteinFood();
        List<FoodItem> fatFoods = dietPlannerService.getFatFood();

        Map<String, Object> response = new HashMap<>();
        response.put("Carbohydrate", carbFoods);
        response.put("Protein", proteinFoods);
        response.put("Fat", fatFoods);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-menu/foods")
    public ResponseEntity<?> setFoods(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MenuRequest menuRequest)
    {
        dietPlannerService.saveFoods(customUserDetails.getUser(), menuRequest);
        return ResponseEntity.ok("Foods added!");
    }

}
