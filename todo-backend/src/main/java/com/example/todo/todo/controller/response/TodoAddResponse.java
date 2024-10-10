package com.example.todo.todo.controller.response;

import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.user.model.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoAddResponse {

    private Long id;

    private String content;

    private LocalDateTime completeAt;

    private LocalDateTime createAt;

    private Boolean complete;

    private UserEntity user;

    public static TodoAddResponse toTodoResponse(TodoEntity entity){
        return TodoAddResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .complete(entity.getComplete())
                .completeAt(entity.getCompleteAt())
                .user(entity.getUser())
                .createAt(entity.getCreateAt())
                .build();
    }
}
