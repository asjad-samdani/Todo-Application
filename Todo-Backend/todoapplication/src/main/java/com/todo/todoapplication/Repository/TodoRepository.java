package com.todo.todoapplication.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.todoapplication.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
  // List<TodoEntity> findByTodoDate(Date date);

  @Query("SELECT t FROM TodoEntity t WHERE t.todoDate BETWEEN :startOfDay AND :endOfDay")
  List<TodoEntity> findTodosByDateRange(@Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay") LocalDateTime endOfDay);
}
