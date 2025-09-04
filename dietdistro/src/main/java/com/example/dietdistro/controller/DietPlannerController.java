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
        // Create a new Menu and save it
        Menu menu = new Menu();
        menuRepository.save(menu);

        // Retrieve the health profile of the user
        HealthProfile healthProfile = healthProfileRepository.findByUser(customUserDetails.getUser()).orElseThrow(() -> new RuntimeException("User not found!"));

        System.out.println("Error at dietcontroller 62 Menu Id : " + menu.getId());
        System.out.println(customUserDetails.getUser().getMenuIds());

        // Add the menu to the user's menu list
        customUserDetails.getUser().getMenuIds().add(menu.getId());

        // Loop over the menu items in the request
        for (MenuItemRequest menuItemRequest : menuRequest.getMenu()) {
            // Create new MenuItem for each item in the request
            MenuItem menuItem = new MenuItem();
            menuItem.setFoodId(menuItemRequest.getFoodId());
            menuItem.setFoodName(menuItemRequest.getFoodName());
            menuItem.setFoodQuantity(menuItemRequest.getFoodQuantity());

            // Add the MenuItem to the Menu
            menu.getMenuItems().add(menuItem);

            // Set the menu reference in the MenuItem
            menuItem.setMenu(menu);
        }

        // Save the menu (this will also persist the menu items due to cascade)
        menuRepository.save(menu);
        userRepository.save(customUserDetails.getUser());
        // Save the health profile if needed
        healthProfileRepository.save(healthProfile);

        // Optionally save other data if required
        // dietPlannerService.saveFoods(customUserDetails.getUser(), menuRequest);

        // Return success response
        return ResponseEntity.ok("Foods added!");
    }


}
