package com.todo.todoapplication.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.todoapplication.Repository.TodoRepository;
import com.todo.todoapplication.entity.TodoEntity;

@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  public List<TodoEntity> getAllTodoes() {
    return todoRepository.findAll();
  }

  public TodoEntity addListOfTodos(TodoEntity entity) {
    return todoRepository.save(entity);

  }

  public boolean deletetodos(Long id) {
    try {
      if (todoRepository.existsById(id)) {
        todoRepository.deleteById(id);
        return true;

      }

    } catch (Exception e) {
      System.out.println(e.getMessage());

    }
    return false;

  }

  public TodoEntity updateTodoDetails(Long id, TodoEntity updatedTodo) {
    if (todoRepository.existsById(id)) {
      TodoEntity existingTodo = todoRepository.findById(id).get();
      existingTodo.setStatus(updatedTodo.getStatus());
      return todoRepository.save(existingTodo);
    }
    return null;
  }

  public List<TodoEntity> findByDate(LocalDateTime date) {
    LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
    // LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);
    LocalDateTime endOfDay = date.toLocalDate().atTime(date.getHour(), date.getMinute());
    return todoRepository.findTodosByDateRange(startOfDay, endOfDay);
  }

}
