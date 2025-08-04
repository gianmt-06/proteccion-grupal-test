package com.example.proteccion.infrastructure.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import com.example.proteccion.domain.entities.TaskStatus;

public record TaskResponseDTO(
    Long taskId,
    String name,
    TaskStatus state,
    LocalDate expirationDate,
    List<UserResponseDTO> assignedUsers
) {
    
}
