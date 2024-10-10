package com.example.todo.todo.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TodoModifyRequest {
    private String content;
}
