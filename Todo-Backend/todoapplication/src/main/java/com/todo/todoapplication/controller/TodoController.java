package com.todo.todoapplication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todoapplication.entity.TodoEntity;
import com.todo.todoapplication.service.TodoService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping("/getTodo")
  public ResponseEntity<?> getTodoes() {
    List<TodoEntity> todos = todoService.getAllTodoes();
    if (todos.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No todos found"));

    } else {
      return ResponseEntity.ok(todos);
    }

  }

  @GetMapping("/todoByDate")
  public ResponseEntity<?> getAllTodosByDate(@RequestParam LocalDateTime todoDate) {
    List<TodoEntity> todo = todoService.findByDate(todoDate);
    if (todo.isEmpty()) {
      return new ResponseEntity<>(todo, HttpStatus.NOT_FOUND);

    } else {
      return new ResponseEntity<>(todo, HttpStatus.OK);
    }

  }

  @PostMapping("/addTodos")
  public ResponseEntity<?> addTodoEntities(@RequestBody TodoEntity todoEntity) {
    todoEntity.setTodoDate(LocalDateTime.now().withSecond(0).withNano(0));
    TodoEntity savedTodoEntity = todoService.addListOfTodos(todoEntity);
    return ResponseEntity.ok(savedTodoEntity);
  }

  @DeleteMapping("deleteTodo/{id}")
  public ResponseEntity<?> deleteTodos(@PathVariable Long id) {
    boolean isDeleted = todoService.deletetodos(id);
    if (isDeleted) {
      return ResponseEntity.ok(Map.of("message", "todo deleted successfully"));

    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "todo id not found"));
    }

  }

  @PutMapping("updateTodo/{id}")
  public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody TodoEntity updatedTodo) {
    TodoEntity todoEntity = todoService.updateTodoDetails(id, updatedTodo);
    if (todoEntity != null) {
      return new ResponseEntity<>(todoEntity, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  }

}
