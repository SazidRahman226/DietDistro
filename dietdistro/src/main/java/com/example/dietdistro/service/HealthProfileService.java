package com.example.dietdistro.service;

import com.example.dietdistro.dto.HealthProfileRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.HealthProfileRepository;
import com.example.dietdistro.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class HealthProfileService {

    private final HealthProfileRepository profileRepo;
    private final UserRepository userRepo;

    public HealthProfile saveOrUpdateProfile(User user, HealthProfileRequest req) {
        // 1. Calculate BMI and BMR
        double bmi = calculateBMI(req.getWeight(), req.getHeight());
        double bmr = calculateBMR(req.getWeight(), req.getHeight(), req.getAge(), req.getGender());

        // 2. Get existing profile or create a new one
        HealthProfile profile;
        if (user.getHealthProfile() != null) {
            // If the user already has a profile, use it
            profile = user.getHealthProfile();
        } else {
            // Otherwise, create a new profile
            profile = new HealthProfile();
            // Since it's a new profile, set the bidirectional relationship
            profile.setUser(user);
            user.setHealthProfile(profile);
        }

        // 3. Update the profile's fields with new data
        profile.setHeight(req.getHeight());
        profile.setWeight(req.getWeight());
        profile.setAge(req.getAge());
        profile.setGender(req.getGender());
        profile.setBmi(bmi);
        profile.setBmr(bmr);

        // 4. Save the profile
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

    public String getName(User user)
    {
        return user.getUsername();
    }
}
