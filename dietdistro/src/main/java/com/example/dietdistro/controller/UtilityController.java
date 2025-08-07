package com.example.dietdistro.controller;

import com.example.dietdistro.dto.UserInfo;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UtilityController {
    private final UserRepository userRepository;
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
}
