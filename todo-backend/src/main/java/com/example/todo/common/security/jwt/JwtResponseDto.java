package com.example.todo.common.security.jwt;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
}
