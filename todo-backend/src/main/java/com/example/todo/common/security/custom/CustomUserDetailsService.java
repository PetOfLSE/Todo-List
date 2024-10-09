package com.example.todo.common.security.custom;

import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}", username);
        UserEntity byName = userRepository.findByUserId(username);
        log.info("user : {}", byName);
        if(byName == null) {
            log.info("User Not Found : CustomUserDetailsService");
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(byName);
    }
}
