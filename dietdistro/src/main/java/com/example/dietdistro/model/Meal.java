package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @ElementCollection
    @CollectionTable(name = "meal_carb_food", joinColumns = @JoinColumn(name = "meal_id"))
    @Column(name = "food_id")
    private Set<FoodEntry> carbFood = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "meal_protein_food", joinColumns = @JoinColumn(name = "meal_id"))
    @Column(name = "food_id")
    private Set<FoodEntry> proteinFood = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "meal_fat_food", joinColumns = @JoinColumn(name = "meal_id"))
    @Column(name = "food_id")
    private Set<FoodEntry> fatFood = new HashSet<>();

}
