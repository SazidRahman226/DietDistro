package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id_seq_gen")
    @SequenceGenerator(name = "menu_id_seq_gen", sequenceName = "menu_id_gen", allocationSize = 1)
    private Long id;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MenuItem> menuItems = new HashSet<>();

}
