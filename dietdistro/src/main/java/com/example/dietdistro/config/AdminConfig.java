package com.example.dietdistro.config;

import com.example.dietdistro.model.Role;
import com.example.dietdistro.model.User;
import com.example.dietdistro.repository.RoleRepository;
import com.example.dietdistro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class AdminConfig implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)
    {
        User user = new User();

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();

        user.setUsername("admin");
        user.setEmail("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRoles(Set.of(userRole, adminRole));

        if(!userRepository.existsByUsername("admin")) {
            userRepository.save(user);
        }
    }
}
