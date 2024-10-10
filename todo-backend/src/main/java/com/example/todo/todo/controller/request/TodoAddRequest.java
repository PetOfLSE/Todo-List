package com.example.todo.todo.controller.request;

import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.user.model.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoAddRequest {

    private String content;

    public static TodoEntity toTodoEntity(TodoAddRequest request, UserEntity user){

        return TodoEntity.builder()
                .content(request.getContent())
                .createAt(LocalDateTime.now())
                .complete(false)
                .user(user)
                .completeAt(null)
                .build();
    }
}
