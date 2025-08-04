package com.example.proteccion.infrastructure.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proteccion.application.mappers.UserMapper;
import com.example.proteccion.application.services.UserService;
import com.example.proteccion.domain.entities.User;
import com.example.proteccion.infrastructure.dtos.requests.AuthRequestDTO;
import com.example.proteccion.infrastructure.dtos.requests.UserCreationDTO;
import com.example.proteccion.infrastructure.dtos.responses.AuthResponse;
import com.example.proteccion.infrastructure.dtos.responses.UserResponseDTO;
import com.example.proteccion.infrastructure.utils.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreationDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User createdUser = userService.createUser(user);

        UserResponseDTO responseUser = UserMapper.toResponseDto(createdUser);

        return ResponseEntity.ok(responseUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(UserMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequestDTO request) {

        User user = userService.getUserByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userService.verifyPassword(request.password(), user.getPassword()))
            throw new RuntimeException("Invalid Credentials");

        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponse(token, user.getId(), user.getEmail());
    }
}