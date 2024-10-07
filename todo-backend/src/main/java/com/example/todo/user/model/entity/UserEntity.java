package com.example.todo.user.model.entity;

import com.example.todo.common.enums.Gender;
import com.example.todo.common.enums.Role;
import com.example.todo.todo.model.entity.TodoEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id", unique = true)
    private String userId;

    @Column(nullable = false, name = "password", unique = true)
    private String password;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime registerAt;

    @OneToMany(mappedBy = "user")
    private List<TodoEntity> todos;
}
