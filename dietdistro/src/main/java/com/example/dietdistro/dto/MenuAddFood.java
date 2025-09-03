package com.example.dietdistro.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MenuAddFood {

    private Long menuId;
    private Set<Long> foodIds; // only IDs
}
