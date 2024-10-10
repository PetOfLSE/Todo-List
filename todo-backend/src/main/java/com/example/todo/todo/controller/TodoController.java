package com.example.todo.todo.controller;

import com.example.todo.todo.controller.request.TodoAddRequest;
import com.example.todo.todo.controller.request.TodoModifyRequest;
import com.example.todo.todo.controller.response.TodoResponse;
import com.example.todo.todo.model.entity.TodoEntity;
import com.example.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @Parameter(name = "id", description = "User id")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add/{id}")
    public ResponseEntity<?> add(
            @PathVariable Long id,
            @RequestBody TodoAddRequest request,
            HttpServletRequest httpServletRequest
    ){
        TodoResponse add = todoService.add(id, request, httpServletRequest);
        return ResponseEntity.ok(add);
    }

    @Operation(summary = "할일 완료")
    @Parameter(name = "id", description = "Todo id")
    @ApiResponse(responseCode = "200", description = "성공시 200 반환")
    @PostMapping("/complete/{id}")
    public ResponseEntity<?> complete(@PathVariable Long id, HttpServletRequest request){
        TodoResponse completes = todoService.completes(id, request);
        return ResponseEntity.ok(completes);
    }

    @Operation(summary = "할일 수정")
    @Parameter(name = "id", description = "Todo id")
    @ApiResponse(responseCode = "200", description = "성공시 200 반환")
    @PatchMapping("/modify/{id}")
    public ResponseEntity<?> patch(
            @PathVariable Long id,
            HttpServletRequest request,
            @RequestBody TodoModifyRequest modifyRequest
            ){
        TodoResponse patch = todoService.patch(id, request, modifyRequest);

        return ResponseEntity.ok(patch);
    }

    @Operation(summary = "할일 삭제")
    @Parameter(name = "id", description = "Todo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공시 200 반환"),
            @ApiResponse(responseCode = "400", description = "실패시 400 반환")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request){
        boolean delete = todoService.delete(id, request);

        return (delete) ? ResponseEntity.ok("삭제가 완료되었습니다.") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제에 실패하였습니다.");
    }
}
