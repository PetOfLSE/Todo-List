package com.example.todo.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(request.getRequestURI().startsWith("/swagger-ui")) {
            log.info("Swagger UI : {}", request.getRequestURI());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String header = request.getHeader("Authorization");
        if(header != null && !header.startsWith("Bearer ")) {
            String token = header.substring(7);
            log.info("token : {}", token);

            if (jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("사용자 정보 저장 : {}", authentication.getName());
            } else {
                log.info("유효한 토큰이 없습니다 : JwtFilter");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
