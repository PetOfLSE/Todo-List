package com.example.todo.common.security.jwt;

import com.example.todo.common.security.custom.CustomUserDetails;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
    
    private final UserRepository userRepository;
    
    @Value("${jwt.secret}")
    private String secret;
    
    public String generateToken(Authentication authentication) {
        String name = authentication.getName();
        log.info("name : {}", name);
        UserEntity user = userRepository.findByUserId(name);
        log.info("user : {}", user);

        LocalDateTime expired = LocalDateTime.now().plusMinutes(30);
        var _expired = Date.from(expired.atZone(ZoneId.systemDefault()).toInstant());
        var key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("auth", user.getRole().name());
        claims.put("exp", _expired);

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(_expired)
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        String name = authentication.getName();

        UserEntity user = userRepository.findByUserId(name);
        log.info("user : {}", user);

        LocalDateTime expired = LocalDateTime.now().plusDays(1);
        var _expired = Date.from(expired.atZone(ZoneId.systemDefault()).toInstant());
        var key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.claims();
        claims.put("auth", user.getRole().name());
        claims.put("exp", _expired);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(_expired)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token){
        return parseToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        var key = Keys.hmacShaKeyFor(secret.getBytes());

        try{
            Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            log.info("body : {}", body.toString());
            return true;
        }catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        String subject = claims.getSubject();
        Long id = Long.parseLong(subject);

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        log.info("user : {} -> JwtUtil", user);

        List<SimpleGrantedAuthority> auth = Arrays.stream(new String[]{claims.get("auth").toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(principal, token, auth);
    }
}
