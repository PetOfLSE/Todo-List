package com.example.todo.user.controller.request;

import com.example.todo.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotNull
    @Max(value = 50)
    private String userId;

    @NotNull
    @Size(min = 4, max = 50)
    private String password;

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    private Gender gender;

}
