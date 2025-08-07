package com.example.dietdistro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FoodItemRequest {

    @NotBlank
    private String foodName;

    private Set< String > category; // Carbohydrate, Protein, Fat

    private Double caloriePerGram;
    @NotBlank
    private String description;
}
