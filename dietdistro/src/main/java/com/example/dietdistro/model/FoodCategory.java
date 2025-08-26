package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_category_seq_gen")
    @SequenceGenerator(name = "food_category_seq_gen", sequenceName = "food_category_seq", allocationSize = 1)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    public FoodCategory(String carbohydrate) {
        name = carbohydrate;
    }
}
