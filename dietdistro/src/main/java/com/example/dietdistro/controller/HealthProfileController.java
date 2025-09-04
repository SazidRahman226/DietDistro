package com.example.dietdistro.controller;

import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.dto.MenuItemRequest;
import com.example.dietdistro.dto.MenuRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.Menu;
import com.example.dietdistro.model.MenuItem;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/health-profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class HealthProfileController {

    private final HealthProfileService profileService;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final HealthProfileRepository healthProfileRepository;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createOrUpdateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody HealthProfileRequest request) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found!"));
        HealthProfile profile = profileService.saveOrUpdateProfile(user, request);
        return ResponseEntity.ok("{\n\t\"bmi\": " + profile.getBmi() + "\n\t\"bmr\": " + profile.getBmr() + "\n}");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        HealthProfile healthProfile = profileService.getId(user.getHealthProfile().getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("age", healthProfile.getAge());
        response.put("height", healthProfile.getHeight());
        response.put("weight", healthProfile.getWeight());
        response.put("gender", healthProfile.getGender());
        response.put("bmi", healthProfile.getBmi());
        response.put("bmr", healthProfile.getBmr());
        response.put("menus", userDetails.getUser().getMenuIds());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getMenu")
    public ResponseEntity<?> getMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HealthProfile healthProfile = healthProfileRepository
                .findByUser(customUserDetails.getUser())
                .orElseThrow(() -> new RuntimeException("Health Profile not found!"));

        Map<Long, MenuRequest> response = new HashMap<>();

        for (Long id : customUserDetails.getUser().getMenuIds()) {
            System.out.println("Error at 82" + " Currwent : " + id);

            MenuRequest menuRequest = new MenuRequest();

            for(MenuItem menuItem : menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found!")).getMenuItems())
            {
                MenuItemRequest menuItemRequest = new MenuItemRequest();

                menuItemRequest.setFoodId(menuItem.getFoodId());
                menuItemRequest.setFoodName(menuItem.getFoodName());
                menuItemRequest.setFoodQuantity(menuItem.getFoodQuantity());

                menuRequest.getMenu().add(menuItemRequest);
            }
            System.out.println("Error at 85");
            response.put(id, menuRequest);
        }
        System.out.println("Error at 87");
        return ResponseEntity.ok(response);
    }
}

