package com.example.dietdistro.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.dietdistro.repository.RoleRepository;
import com.example.dietdistro.model.Role;

@Component
public class RoleConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        // Add roles only if they don't exist
        if (!roleRepository.existsByName("USER")) {
            roleRepository.save(new Role("USER"));
        }
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.save(new Role("ADMIN"));
        }
    }
}