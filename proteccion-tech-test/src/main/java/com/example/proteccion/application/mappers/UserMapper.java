package com.example.proteccion.application.mappers;

import com.example.proteccion.domain.entities.User;
import com.example.proteccion.infrastructure.dtos.requests.UserCreationDTO;
import com.example.proteccion.infrastructure.dtos.responses.UserResponseDTO;

public class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static User toEntity(UserCreationDTO dto) {
        return User.builder()
            .name(dto.firstName())
            .lastName(dto.lastName()) 
            .email(dto.email())
            .password(dto.password())
            .build();
    }

    public static UserResponseDTO toResponseDto(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRol()
        );
    }
}
