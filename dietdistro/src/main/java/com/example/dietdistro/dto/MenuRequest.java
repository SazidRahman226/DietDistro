package com.example.dietdistro.dto;

import com.example.dietdistro.model.Meal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MenuRequest {

    private Long menuId;

    private Meal breakfast; // only IDs
    private Meal lunch; // only IDs
    private Meal dinner; // only IDs

}
