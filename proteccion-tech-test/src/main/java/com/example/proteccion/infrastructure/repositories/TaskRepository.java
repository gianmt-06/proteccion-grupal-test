package com.example.proteccion.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proteccion.domain.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}