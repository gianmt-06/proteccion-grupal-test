package com.example.proteccion.domain.entities;

import jakarta.persistence.*;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String lastName;
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role rol = Role.USER;

    @OneToMany(mappedBy = "user")
    private Set<UsersTasks> usersTasks;
}
