package com.example.dietdistro.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotEmpty(message = "Username cannot be empty!\n")
    @NotBlank
    private String username;

    @NotEmpty(message = "Email cannot be empty!\n")
    @NotBlank
    private String email;

    @NotEmpty(message = "Password cannot be empty!\n")
    @NotBlank
    private String password;
}
