package com.example.todo.user.controller.request;

import com.example.todo.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @Email
    private String email;

    @NotNull
    private Gender gender;
}
