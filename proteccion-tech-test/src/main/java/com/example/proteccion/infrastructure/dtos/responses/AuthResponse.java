package com.example.proteccion.infrastructure.dtos.responses;

public record AuthResponse(
    String token,
    Long uid,
    String email
) {}
