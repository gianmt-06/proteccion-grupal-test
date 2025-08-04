package com.example.proteccion.infrastructure.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDTO(
    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @Email
    @NotBlank
    String email,

    @NotBlank
    String password
) {}