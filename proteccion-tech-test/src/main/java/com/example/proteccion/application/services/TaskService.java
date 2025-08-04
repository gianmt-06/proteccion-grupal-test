package com.example.proteccion.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.proteccion.domain.entities.Task;
import com.example.proteccion.domain.entities.User;
import com.example.proteccion.domain.entities.UsersTasks;
import com.example.proteccion.infrastructure.repositories.TaskRepository;
import com.example.proteccion.infrastructure.repositories.UserRepository;
import com.example.proteccion.infrastructure.repositories.UsersTasksRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private UserRepository userRepository;
    private UsersTasksRepository userTasksRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            UsersTasksRepository usersTasksRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userTasksRepository = usersTasksRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return taskRepository.findById(taskId).map(task -> {
            task.setName(updatedTask.getName());
            task.setState(updatedTask.getState());
            task.setExpirationDate(updatedTask.getExpirationDate());
            return taskRepository.save(task);
        });
    }

    @Transactional
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UsersTasks usersTasks = new UsersTasks();
        usersTasks.setTask(task);
        usersTasks.setUser(user);
        usersTasks.setTaskId(task.getTaskId());
        usersTasks.setUserId(user.getId());

        userTasksRepository.save(usersTasks);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUserId(Long userId) {
        List<UsersTasks> usersTasks = userTasksRepository.findByUserId(userId);
        return usersTasks.stream()
                .map(UsersTasks::getTask)
                .toList();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
