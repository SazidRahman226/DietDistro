package com.example.dietdistro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FoodItemRequest {

    @NotBlank
    private String foodName;

    private String[] category; // Carbohydrate, Protein, Fat

    private Double caloriePerGram;
    @NotBlank
    private String description;
}
