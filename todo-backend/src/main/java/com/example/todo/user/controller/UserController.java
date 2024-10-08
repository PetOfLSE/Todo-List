package com.example.todo.user.controller;

import com.example.todo.common.security.jwt.JwtRefreshDto;
import com.example.todo.common.security.jwt.JwtResponseDto;
import com.example.todo.user.controller.request.LoginRequest;
import com.example.todo.user.controller.request.RegisterRequest;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공시 200")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserEntity register = userService.register(registerRequest);
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공시 200")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("login request: {}", loginRequest);
        JwtResponseDto login = userService.login(loginRequest);

        response.setHeader("Authorization", "Bearer " + login.getAccessToken());
        response.setHeader("Refresh-Token", login.getRefreshToken());

        return ResponseEntity.ok(login);
    }

    @Operation(summary = "Access token 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공시 200 반환")
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        JwtRefreshDto accessToken = userService.refresh(request);

        response.setHeader("Authorization", "Bearer " + accessToken);
        return ResponseEntity.ok(accessToken);
    }


}
