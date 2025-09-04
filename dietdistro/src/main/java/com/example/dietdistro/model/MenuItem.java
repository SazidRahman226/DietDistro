package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_id_seq_gen")
    @SequenceGenerator(name = "menu_item_id_seq_gen", sequenceName = "id_gen", allocationSize = 1)
    private Long id;

    private Long foodId;
    private String foodName;
    private Double foodQuantity;

    // Define ManyToOne relationship to Menu entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")  // This creates the foreign key in the menu_item table
    private Menu menu;
}
