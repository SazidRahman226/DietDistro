package com.example.dietdistro.controller;

import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.User;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-profile")
@RequiredArgsConstructor
public class HealthProfileController {

    private final HealthProfileService profileService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody HealthProfileRequest request) {
        User user = userDetails.getUser();
        HealthProfile profile = profileService.saveOrUpdateProfile(user, request);
        return ResponseEntity.ok("{\n\t\"bmi\": " + profile.getBmi() + "\n\t\"bmr\": " + profile.getBmr() + "\n}");
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return profileService.getProfile(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
