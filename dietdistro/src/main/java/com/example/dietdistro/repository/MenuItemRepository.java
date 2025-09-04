package com.example.dietdistro.repository;

import com.example.dietdistro.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    // Custom queries can be added here if necessary
}
