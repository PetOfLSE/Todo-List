package com.example.todo.user.service;

import com.example.todo.common.converter.Converter;
import com.example.todo.user.controller.request.RegisterRequest;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Converter converter;

    public UserEntity register(RegisterRequest registerRequest) {
        UserEntity user = converter.toUserEntity(registerRequest);
        UserEntity save = userRepository.save(user);

        return save;
    }
}
