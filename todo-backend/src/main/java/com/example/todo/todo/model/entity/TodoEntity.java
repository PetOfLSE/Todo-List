package com.example.todo.todo.model.entity;

import com.example.todo.user.model.entity.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "complete_at")
    private LocalDateTime completeAt;

    @Column(nullable = false, name = "create_at")
    private LocalDateTime createAt;

    @Column(nullable = false, name = "complete")
    private Boolean complete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
