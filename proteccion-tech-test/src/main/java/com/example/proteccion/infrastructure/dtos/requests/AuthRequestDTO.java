package com.example.proteccion.infrastructure.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
    @NotBlank
    String email,
    @NotBlank
    String password
) {
}
