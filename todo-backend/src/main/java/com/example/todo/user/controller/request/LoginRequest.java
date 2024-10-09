package com.example.todo.user.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {
    private String userId;

    private String password;
}
