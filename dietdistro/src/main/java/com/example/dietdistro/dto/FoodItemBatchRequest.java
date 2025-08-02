package com.example.dietdistro.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FoodItemBatchRequest {

    @NotEmpty(message = "Food item list cannot be empty!\n")
    @Valid
    List < FoodItemRequest > foodItems;

}
