package com.example.dietdistro.controller;

import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-profile")
@RequiredArgsConstructor
public class HealthProfileController {

    private final HealthProfileService profileService;
    private final UserRepository userRepository;

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
        HealthProfile healthProfile = profileService.getId(user.getHealthProfile().getId()).orElseThrow(() -> new RuntimeException("User not found!"));

        return ResponseEntity.ok(
                "{" +
                        "\n\t\"username\": " + "\"" + user.getUsername() + "\"" +
                        "\n\t\"age\": " + healthProfile.getAge() +
                        "\n\t\"height\":" + healthProfile.getHeight() +
                        "\n\t\"weight\":" + healthProfile.getWeight() +
                        "\n\t\"bmi\":" + healthProfile.getBmi() +
                        "\n\t\"bmr\":" + healthProfile.getBmr() +
                "\n}"
        );
    }
}
