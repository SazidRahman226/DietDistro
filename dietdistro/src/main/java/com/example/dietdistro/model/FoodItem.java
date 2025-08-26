package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_item_seq_gen")
    @SequenceGenerator(name = "food_item_seq_gen", sequenceName = "food_item_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String foodName;

    @ManyToMany
    @JoinTable(
            name = "FoodItemCategory",
            joinColumns = @JoinColumn(name = "food_item_id"),
            inverseJoinColumns = @JoinColumn(name = "food_category_id")
    )
    private Set<FoodCategory> categories = new HashSet<>();

    // per 100 gm
    @Column(nullable = false) private Double calorie;
    @Column private Double carbohydrate;
    @Column private Double protein;
    @Column private Double fat;


    @Column(nullable = false)
    private String description;
}
