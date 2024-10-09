package com.example.todo.user.service;

import com.example.todo.common.converter.Converter;
import com.example.todo.common.security.jwt.JwtResponseDto;
import com.example.todo.common.security.jwt.JwtUtil;
import com.example.todo.user.controller.request.LoginRequest;
import com.example.todo.user.controller.request.RegisterRequest;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Converter converter;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserEntity register(RegisterRequest registerRequest) {
        UserEntity user = converter.toUserEntity(registerRequest);
        UserEntity save = userRepository.save(user);

        return save;
    }

    public JwtResponseDto login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        String accessToken = jwtUtil.generateToken(authenticate);
        String refreshToken = jwtUtil.generateRefreshToken(authenticate);

        return JwtResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
