package com.example.todo.todo.controller;

import com.example.todo.todo.controller.request.TodoAddRequest;
import com.example.todo.todo.controller.response.TodoAddResponse;
import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/todo")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "할일 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공시 200 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add/{id}")
    public ResponseEntity<?> add(@PathVariable Long id, @RequestBody TodoAddRequest request){
        TodoAddResponse add = todoService.add(id, request);
        return ResponseEntity.ok(add);
    }
}
