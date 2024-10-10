package com.example.todo.user.service;

import com.example.todo.common.enums.Role;
import com.example.todo.common.security.jwt.JwtRefreshDto;
import com.example.todo.common.security.jwt.JwtResponseDto;
import com.example.todo.common.security.jwt.JwtUtil;
import com.example.todo.user.controller.request.LoginRequest;
import com.example.todo.user.controller.request.RegisterRequest;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static PasswordEncoder passwordEncoder;

    public UserEntity register(RegisterRequest registerRequest) {
        UserEntity user = toUserEntity(registerRequest);
        UserEntity save = userRepository.save(user);

        return save;
    }

    public JwtResponseDto login(LoginRequest loginRequest) {
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

            String accessToken = jwtUtil.generateToken(authenticate);
            String refreshToken = jwtUtil.generateRefreshToken(authenticate);

            return JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }catch (Exception e){
            log.info("UserService login Fail : {}", e.getMessage());
        }

        return null;

    }

    public JwtRefreshDto refresh(HttpServletRequest request) {
        String token = request.getHeader("Refresh-Token");
        log.info("token : {}", token);

        try{
            if (token != null) {
                Claims claims = jwtUtil.parseToken(token);
                log.info("claim : {}", claims.toString());

                Date exp = new Date(((Integer) claims.get("exp")).longValue() * 1000);
                if (exp.after(new Date())) {

                    Long id = Long.valueOf((Integer) claims.get("id"));
                    UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
                    String accessToken = jwtUtil.generateToken(authentication);

                    return JwtRefreshDto.builder()
                            .accessToken(accessToken)
                            .build();
                } else {
                    throw new RuntimeException("Refresh Token 만료");
                }
            }
        }catch (Exception e){
            log.info("UserService refresh Fail : {}", e.getMessage());
        }

        throw new RuntimeException("UserService refresh Fail");
    }

    public static UserEntity toUserEntity(RegisterRequest request) {
        return UserEntity.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .role(Role.ROLE_USER)
                .gender(request.getGender())
                .registerAt(LocalDateTime.now())
                .build();
    }
}
