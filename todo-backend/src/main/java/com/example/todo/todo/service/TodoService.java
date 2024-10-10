package com.example.todo.todo.service;

import com.example.todo.common.security.jwt.JwtUtil;
import com.example.todo.todo.controller.request.TodoAddRequest;
import com.example.todo.todo.controller.request.TodoModifyRequest;
import com.example.todo.todo.controller.response.TodoResponse;
import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.todo.model.repository.TodoRepository;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TodoResponse add(Long id, TodoAddRequest request) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        TodoEntity todoEntity = TodoAddRequest.toTodoEntity(request, user);
        TodoEntity save = todoRepository.save(todoEntity);

        TodoResponse response = TodoResponse.toTodoResponse(save);
        return response;
    }

    public TodoResponse patch(Long id, HttpServletRequest request, TodoModifyRequest modifyRequest) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            Claims claims = jwtUtil.parseToken(token);
            Long userId = Long.parseLong(String.valueOf(claims.get("id", Integer.class)));

            log.info("id : {}", userId);

            TodoEntity todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo id 가 잘못되었습니다."));
            UserEntity user = todo.getUser();
            if(user.getId() == userId) {
                if(modifyRequest.getContent() != null){
                    todo.setContent(modifyRequest.getContent());
                    TodoEntity save = todoRepository.save(todo);
                    return TodoResponse.builder()
                            .id(save.getId())
                            .content(save.getContent())
                            .completeAt(save.getCompleteAt())
                            .createAt(save.getCreateAt())
                            .complete(save.getComplete())
                            .build();

                }
            }else{
                throw new RuntimeException("사용자가 다릅니다.");
            }
        }
        return null;
    }
}
