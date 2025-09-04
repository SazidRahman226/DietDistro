package com.example.dietdistro.dto;

import com.example.dietdistro.model.MenuItem;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MenuRequest {

    private Set<MenuItemRequest> menu;

}
