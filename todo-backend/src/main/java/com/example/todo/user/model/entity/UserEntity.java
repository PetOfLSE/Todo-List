package com.example.todo.user.model.entity;

import com.example.todo.common.enums.Gender;
import com.example.todo.common.enums.Role;
import com.example.todo.todo.model.entity.TodoEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @Column(nullable = false, name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "register_at")
    private LocalDateTime registerAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    // EAGER 은 UserEntity 가 로드될때마다 todos 를 가져옴 성능에 영향을 미칠 수 있음
    // TODO 수정 필요
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<TodoEntity> todos;

}
