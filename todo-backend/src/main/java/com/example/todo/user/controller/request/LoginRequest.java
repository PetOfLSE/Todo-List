package com.example.todo.user.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String userId;

    private String password;
}
