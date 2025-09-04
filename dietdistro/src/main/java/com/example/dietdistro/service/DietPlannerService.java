package com.example.dietdistro.service;

import com.example.dietdistro.dto.*;
import com.example.dietdistro.model.*;
import com.example.dietdistro.repository.*;
import com.example.dietdistro.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietPlannerService {

    private final FoodItemRepository foodItemRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final HealthProfileRepository healthProfileRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public List<FoodItem> getCarbFood()
    {
        FoodCategory carbCategory = foodCategoryRepository.findByName("Carbohydrate")
                .orElseThrow(() -> new IllegalArgumentException("Category 'Carbohydrates' not found"));

        return foodItemRepository.findByCategories(carbCategory);
    }

    public List<FoodItem> getProteinFood()
    {
        FoodCategory proteinCategory = foodCategoryRepository.findByName("Protein")
                .orElseThrow(() -> new IllegalArgumentException("Category 'Protein' not found"));

        return foodItemRepository.findByCategories(proteinCategory);
    }

    public List<FoodItem> getFatFood()
    {
        FoodCategory fatCategory = foodCategoryRepository.findByName("Fat")
                .orElseThrow(() -> new IllegalArgumentException("Category 'Fat' not found"));

        return foodItemRepository.findByCategories(fatCategory);
    }

    public void saveMenuRequest(CustomUserDetails customUserDetails, MenuRequest menuRequest) {

        Menu menu = new Menu();
        customUserDetails.getUser().getMenuIds().add(menu.getId());

        for (MenuItemRequest menuItemRequest : menuRequest.getMenu()) {

            MenuItem menuItem = new MenuItem();

            menuItem.setFoodId(menuItemRequest.getFoodId());
            menuItem.setFoodName(menuItemRequest.getFoodName());
            menuItem.setFoodQuantity(menuItemRequest.getFoodQuantity());


            menu.getMenuItems().add(menuItem);
            menuItem.setMenu(menu);
        }


        menuRepository.save(menu);
        userRepository.save(customUserDetails.getUser());
    }

}
