package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "health_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_id_seq_gen")
    @SequenceGenerator(name = "health_id_seq_gen", sequenceName = "id_gen", allocationSize = 1)
    private Long id;

    private double height; // in cm
    private double weight; // in kg
    private int age;
    private String gender; // "Male" or "Female"

    private double bmi;
    private double bmr;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
