package com.example.dietdistro.controller;

import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.DietPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietPlannerController {

    private final DietPlannerService dietPlannerService;

    @GetMapping("/carbohydrate")
    public ResponseEntity<?> getCarbohydrate(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> carbFoods = dietPlannerService.getCarbFood();
        return ResponseEntity.ok(carbFoods);
    }

    @GetMapping("/protein")
    public ResponseEntity<?> getProtein(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> proteinFoods = dietPlannerService.getProteinFood();
        return ResponseEntity.ok(proteinFoods);
    }

    @GetMapping("/fat")
    public ResponseEntity<?> getFat(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {

        List<FoodItem> fatFoods = dietPlannerService.getFatFood();
        return ResponseEntity.ok(fatFoods);
    }

}
