package com.example.dietdistro.controller;

import com.example.dietdistro.dto.MenuItemRequest;
import com.example.dietdistro.dto.MenuRequest;
import com.example.dietdistro.model.FoodItem;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.Menu;
import com.example.dietdistro.model.MenuItem;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.DietPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DietPlannerController {

    private final DietPlannerService dietPlannerService;
    private final MenuRepository menuRepository;
    private final HealthProfileRepository healthProfileRepository;
    private final UserRepository userRepository;

    @GetMapping("/create-menu")
    public ResponseEntity<?> createMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        Menu menu = new Menu();
        HealthProfile healthProfile = healthProfileRepository.findByUser(customUserDetails.getUser()).orElseThrow(() -> new RuntimeException("User not found!"));
        customUserDetails.getUser().getMenuIds().add(menu.getId());
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
    public ResponseEntity<?> setFoods(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MenuRequest menuRequest) {
        dietPlannerService.saveMenuRequest(customUserDetails, menuRequest);
        return ResponseEntity.ok("Foods added!");
    }

    @GetMapping("/social/show")
    public ResponseEntity<?> showAllMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        Map<String, Set<MenuRequest>> allUserMenuList = dietPlannerService.getAllUserMenuList(customUserDetails);
        return ResponseEntity.ok(allUserMenuList);
    }


}
