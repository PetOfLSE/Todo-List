package com.example.todo.todo.service;

import com.example.todo.todo.controller.request.TodoAddRequest;
import com.example.todo.todo.controller.response.TodoAddResponse;
import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.todo.model.repository.TodoRepository;
import com.example.todo.user.model.entity.UserEntity;
import com.example.todo.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public TodoAddResponse add(Long id, TodoAddRequest request) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        TodoEntity todoEntity = TodoAddRequest.toTodoEntity(request, user);
        TodoEntity save = todoRepository.save(todoEntity);

        TodoAddResponse response = TodoAddResponse.toTodoResponse(save);
        return response;
    }
}
