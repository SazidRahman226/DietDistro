package com.example.dietdistro.model;

import com.example.dietdistro.model.User;
import jakarta.persistence.*;
import lombok.*;

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

    @ElementCollection
    private Set<Long> carbFood = new HashSet<>();
    @ElementCollection
    private Set<Long> proteinFood = new HashSet<>();
    @ElementCollection
    private Set<Long> fatFood = new HashSet<>();

}