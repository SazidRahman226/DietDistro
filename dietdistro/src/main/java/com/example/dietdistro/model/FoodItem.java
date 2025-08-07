package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

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

    @Column(nullable = false)
    private Set < String > category;

    @Column(nullable = false)
    private Double caloriePerGram;

    @Column(nullable = false)
    private String description;
}
