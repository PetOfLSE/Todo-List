package com.example.todo.common.security.jwt;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRefreshDto {
    private String accessToken;
}
