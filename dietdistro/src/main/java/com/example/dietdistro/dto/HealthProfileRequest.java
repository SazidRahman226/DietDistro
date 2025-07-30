package com.example.dietdistro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HealthProfileRequest {
    @Positive(message = "Height must be positive")
    private double height; // cm

    @Positive(message = "Weight must be positive")
    private double weight; // kg

    @Min(10) @Max(120)
    private int age;

    @NotBlank
    private String gender; // "Male" or "Female"
}
