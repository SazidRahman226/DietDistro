package com.example.dietdistro.controller;

import com.example.dietdistro.dto.SigninRequest;
import com.example.dietdistro.dto.SignupRequest;
import com.example.dietdistro.model.HealthProfile;
import com.example.dietdistro.model.Role;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.RoleRepository;
import com.example.dietdistro.repository.UserRepository;
import com.example.dietdistro.security.AuthEntryPointJwt;
import com.example.dietdistro.security.CustomUserDetails;
import com.example.dietdistro.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.aspectj.util.IStructureModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthEntryPointJwt authEntryPointJwt;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {

        if (userRepo.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        if (userRepo.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
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
