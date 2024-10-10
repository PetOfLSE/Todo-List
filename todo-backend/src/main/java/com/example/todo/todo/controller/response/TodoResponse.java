package com.example.todo.todo.controller.response;

import com.example.todo.todo.model.entity.TodoEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoResponse {

    private Long id;

    private String content;

    private LocalDateTime completeAt;

    private LocalDateTime createAt;

    private Boolean complete;

    public static TodoResponse toTodoResponse(TodoEntity entity){
        return TodoResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .complete(entity.getComplete())
                .completeAt(entity.getCompleteAt())
                .createAt(entity.getCreateAt())
                .build();
    }
}
