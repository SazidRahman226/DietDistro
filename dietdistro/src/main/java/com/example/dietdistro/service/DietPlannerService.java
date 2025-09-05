package com.example.dietdistro.service;

import com.example.dietdistro.dto.*;
import com.example.dietdistro.model.*;
import com.example.dietdistro.repository.*;
import com.example.dietdistro.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public void saveMenuRequest(CustomUserDetails customUserDetails, MenuRequest menuRequest) {

        Menu menu = new Menu();
        menuRepository.save(menu);

        customUserDetails.getUser().getMenuIds().add(menu.getId());

        for (MenuItemRequest menuItemRequest : menuRequest.getMenu()) {
            MenuItem menuItem = new MenuItem();

            menuItem.setFoodId(menuItemRequest.getFoodId());
            menuItem.setFoodName(menuItemRequest.getFoodName());
            menuItem.setFoodQuantity(menuItemRequest.getFoodQuantity());

            menu.getMenuItems().add(menuItem);

            menuItem.setMenu(menu);
        }

        userRepository.save(customUserDetails.getUser());
        menuRepository.save(menu);
    }



    public Map<String, Set<MenuRequest>> getAllUserMenuList(CustomUserDetails customUserDetails)
    {
        Map<String, Set<MenuRequest>> allUserMenuList = new HashMap<>();

        List<User> users = userRepository.findAll();

        for(User user : users)
        {
            Set<MenuRequest> allUserMenuRequest = new HashSet<>();
            for(Long menuId : user.getMenuIds())
            {
                Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found!"));
                MenuRequest menuRequest = new MenuRequest();
                for(MenuItem menuItem : menu.getMenuItems())
                {
                    MenuItemRequest menuItemRequest = new MenuItemRequest(menuItem.getFoodId(), menuItem.getFoodName(), menuItem.getFoodQuantity());
                    menuRequest.getMenu().add(menuItemRequest);
                }
                allUserMenuRequest.add(menuRequest);
            }
            allUserMenuList.put(user.getUsername(), allUserMenuRequest);
        }

        return allUserMenuList;
    }


}
