package com.example.dietdistro.service;

import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.dto.MenuItemRequest;
import com.example.dietdistro.dto.MenuRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.MenuItem;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.MenuRepository;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.CustomUserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class HealthProfileService {

    private final HealthProfileRepository profileRepo;
    private final UserRepository userRepo;
    private final MenuRepository menuRepository;

    public HealthProfile saveOrUpdateProfile(User user, HealthProfileRequest req) {

        double bmi = calculateBMI(req.getWeight(), req.getHeight());
        double bmr = calculateBMR(req.getWeight(), req.getHeight(), req.getAge(), req.getGender());

        HealthProfile profile;
        if (user.getHealthProfile() != null) {

            profile = user.getHealthProfile();
        } else {

            profile = new HealthProfile();
            profile.setUser(user);
            user.setHealthProfile(profile);
        }

        profile.setHeight(req.getHeight());
        profile.setWeight(req.getWeight());
        profile.setAge(req.getAge());
        profile.setGender(req.getGender());
        profile.setBmi(bmi);
        profile.setBmr(bmr);

        return profileRepo.save(profile);
    }



    private double calculateBMI(double weight, double heightCm) {
        double heightM = heightCm / 100.0;
        return weight / (heightM * heightM);
    }

    private double calculateBMR(double weight, double heightCm, int age, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return 88.362 + (13.397 * weight) + (4.799 * heightCm) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * heightCm) - (4.330 * age);
        }
    }

    public Optional<HealthProfile> getId(Long id) {
        return profileRepo.findById(id);
    }


    public Map<Long, MenuRequest> fetchResponse(CustomUserDetails customUserDetails)
    {
        Map<Long, MenuRequest> response = new HashMap<>();
        for (Long id : customUserDetails.getUser().getMenuIds()) {
            MenuRequest menuRequest = new MenuRequest();

            for(MenuItem menuItem : menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found!")).getMenuItems())
            {
                MenuItemRequest menuItemRequest = new MenuItemRequest();

                menuItemRequest.setFoodId(menuItem.getFoodId());
                menuItemRequest.setFoodName(menuItem.getFoodName());
                menuItemRequest.setFoodQuantity(menuItem.getFoodQuantity());

                menuRequest.getMenu().add(menuItemRequest);
            }
            response.put(id, menuRequest);
        }
        return response;
    }
}
