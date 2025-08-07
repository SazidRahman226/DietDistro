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
        double bmi = calculateBMI(req.getWeight(), req.getHeight());
        double bmr = calculateBMR(req.getWeight(), req.getHeight(), req.getAge(), req.getGender());



//        HealthProfile profile = profileRepo.findByUser(user)
//                .orElse(new HealthProfile());
//        profile.setUser(user);
        profile.setHeight(req.getHeight());
        profile.setWeight(req.getWeight());
        profile.setAge(req.getAge());
        profile.setGender(req.getGender());
        profile.setBmi(bmi);
        profile.setBmr(bmr);
        user.setHealthProfile(profile);
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

    public Optional<HealthProfile> getProfile(User user) {
        return profileRepo.findByUser(user);
    }

    public String getName(User user)
    {
        return user.getUsername();
    }
}
