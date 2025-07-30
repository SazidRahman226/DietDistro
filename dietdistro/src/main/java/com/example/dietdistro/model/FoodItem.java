package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_item_seq_gen")
    @SequenceGenerator(name = "food_item_seq_gen", sequenceName = "food_item_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String foodName;

    @Column(nullable = false)
    private String[] category;

    @Column(nullable = false)
    private Double caloriePerGram;

    @Column(nullable = false)
    private String description;
}
