package com.example.dietdistro.config;

import com.example.dietdistro.model.FoodCategory;
import com.example.dietdistro.repository.FoodCategoryRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.dietdistro.repository.RoleRepository;
import com.example.dietdistro.model.Role;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class FoodCategoryConfig implements CommandLineRunner {

    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public void run(String... args) {

        if (!foodCategoryRepository.existsByName("Carbohydrate")) {
            foodCategoryRepository.save(new FoodCategory("Carbohydrate"));
        }

        if (!foodCategoryRepository.existsByName("Protein")) {
            foodCategoryRepository.save(new FoodCategory("Protein"));
        }

        if (!foodCategoryRepository.existsByName("Fat")) {
            foodCategoryRepository.save(new FoodCategory("Fat"));
        }
    }
}