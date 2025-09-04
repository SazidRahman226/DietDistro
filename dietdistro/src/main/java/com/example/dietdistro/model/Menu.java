package com.example.dietdistro.model;

import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.FoodItemRepository;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id_seq_gen")
    @SequenceGenerator(name = "menu_id_seq_gen", sequenceName = "id_gen", allocationSize = 1)
    private Long id;

    @Embedded
    private Meal breakfast;
    @Embedded
    private Meal lunch;
    @Embedded
    private Meal dinner;

    private Double totalCalorie;

}