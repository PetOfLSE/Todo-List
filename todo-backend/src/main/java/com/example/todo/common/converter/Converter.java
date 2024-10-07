package com.example.todo.common.converter;

import com.example.todo.user.controller.request.RegisterRequest;
import com.example.todo.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Converter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(RegisterRequest request) {
        return UserEntity.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .gender(request.getGender())
                .registerAt(LocalDateTime.now())
                .build();
    }
}
