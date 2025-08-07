package com.example.dietdistro.controller;

import com.example.dietdistro.dto.UserInfo;
import com.example.dietdistro.model.Role;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UtilityController {
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-info/{username}")
    public ResponseEntity<?> userInfo(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error!"));
        UserInfo userInfo = new UserInfo();

        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setHeight(user.getHealthProfile().getHeight());
        userInfo.setWeight(user.getHealthProfile().getWeight());
        userInfo.setAge(user.getHealthProfile().getAge());
        userInfo.setGender(user.getHealthProfile().getGender());
        userInfo.setBmi(user.getHealthProfile().getBmi());
        userInfo.setBmr(user.getHealthProfile().getBmr());

        return ResponseEntity.ok(userInfo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin-access")
    public ResponseEntity<?> adminAccess(@RequestBody String username)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error!"));
        user.getRoles().add(new Role("ROLE_ADMIN"));

        return ResponseEntity.ok("Admin added");
    }

}
