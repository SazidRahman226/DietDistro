package com.example.dietdistro.dto;

import com.example.dietdistro.model.FoodCategory;
import com.example.dietdistro.model.FoodItem;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FoodItemRequest extends FoodItem {

    @NotBlank
    private String foodName;

    private Set<FoodCategory> foodCategories; // Carbohydrate, Protein, Fat

    // total calories per 100 gm
    private Double calorie;
    // carbohydrate calories per 100 gm food
    private Double carbohydrate;
    // protein calories per 100 gm food
    private Double protein;
    // fat calories per 100 gm food
    private Double fat;

    @NotBlank
    private String description;
}
