package com.example.dietdistro.controller;

import com.example.dietdistro.dto.SigninRequest;
import com.example.dietdistro.model.Role;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.RoleRepository;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.IStructureModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        Role userRole = roleRepo.findByName("USER").orElseThrow();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(userRole));
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SigninRequest user) {

        User _user = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(user.getPassword(), _user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(_user.getUsername());
        return ResponseEntity.ok(token);
    }
}
