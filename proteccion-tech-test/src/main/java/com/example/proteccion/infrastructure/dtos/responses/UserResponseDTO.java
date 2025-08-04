package com.example.proteccion.infrastructure.dtos.responses;

import com.example.proteccion.domain.entities.Role;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    Role role
) {}