package com.example.proteccion.domain.entities;

import java.io.Serializable;
import java.util.Objects;


public class UsersTasksId implements Serializable {
    private Long userId;
    private Long taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersTasksId)) return false;
        UsersTasksId that = (UsersTasksId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taskId);
    }
}
