package com.example.todo.common.security.custom;

import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byName = userRepository.findByName(username);
        if(byName == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(byName);
    }
}
