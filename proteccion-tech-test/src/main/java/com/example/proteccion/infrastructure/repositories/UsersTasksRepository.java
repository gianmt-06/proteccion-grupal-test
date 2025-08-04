package com.example.proteccion.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proteccion.domain.entities.UsersTasks;
import com.example.proteccion.domain.entities.UsersTasksId;

@Repository
public interface UsersTasksRepository extends JpaRepository<UsersTasks, UsersTasksId> {
    List<UsersTasks> findByUserId(Long userId);
}