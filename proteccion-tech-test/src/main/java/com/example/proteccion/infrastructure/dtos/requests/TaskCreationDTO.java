package com.example.proteccion.infrastructure.dtos.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreationDTO(
    @NotBlank
    String name,
    LocalDate expirationDate,

    @NotNull
    Long creatorId

) {}