package com.example.dietdistro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "health_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_id_seq_gen")
    @SequenceGenerator(name = "health_id_seq_gen", sequenceName = "health_id_gen", allocationSize = 1)
    private Long id;


    private double height; // in cm
    private double weight; // in kg
    private int age;
    private String gender; // "Male" or "Female"

    private double bmi;
    private double bmr;

    @OneToOne(mappedBy = "healthProfile", fetch = FetchType.LAZY)
    private User user;


}
