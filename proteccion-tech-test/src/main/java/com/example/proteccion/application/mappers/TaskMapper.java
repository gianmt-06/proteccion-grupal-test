package com.example.proteccion.application.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.proteccion.domain.entities.Task;
import com.example.proteccion.domain.entities.UsersTasks;
import com.example.proteccion.infrastructure.dtos.responses.TaskResponseDTO;
import com.example.proteccion.infrastructure.dtos.responses.UserResponseDTO;

public class TaskMapper {

    private TaskMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static TaskResponseDTO toResponseDto(Task task) {
        List<UserResponseDTO> users = task.getUsersTasks()
            .stream()
            .map(UsersTasks::getUser)
            .map(UserMapper::toResponseDto)
            .collect(Collectors.toList());

        return new TaskResponseDTO(
            task.getTaskId(),
            task.getName(),
            task.getState(),
            task.getExpirationDate(),
            users
        );
    }
}
