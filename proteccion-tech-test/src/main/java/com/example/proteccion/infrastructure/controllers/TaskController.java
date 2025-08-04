package com.example.proteccion.infrastructure.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proteccion.application.mappers.TaskMapper;
import com.example.proteccion.application.services.TaskService;
import com.example.proteccion.domain.entities.Task;
import com.example.proteccion.infrastructure.dtos.requests.TaskCreationDTO;
import com.example.proteccion.infrastructure.dtos.responses.TaskResponseDTO;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    @PostMapping("/")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreationDTO taskDto) {
        Task task = Task.builder()
                .name(taskDto.name())
                .expirationDate(taskDto.expirationDate())
                .build();

        Task createdTask = taskService.createTask(task);

        taskService.assignTaskToUser(createdTask.getTaskId(), taskDto.creatorId());

        System.out.println("ID TASK:" + createdTask.getTaskId());

        Task updatedTask = taskService.getTaskById(createdTask.getTaskId());

        TaskResponseDTO response = TaskMapper.toResponseDto(updatedTask);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            TaskResponseDTO response = TaskMapper.toResponseDto(task);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskCreationDTO taskDto) {
        return taskService.updateTask(id, Task.builder()
                .name(taskDto.name())
                // .state(taskDto.state())
                .expirationDate(taskDto.expirationDate())
                .build())
                .map(TaskMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Void> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId)
                .stream()
                .map(TaskMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasks);
    }
}