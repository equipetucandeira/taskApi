package com.ifsp.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ifsp.task.dto.TaskRequestDTO;
import com.ifsp.task.dto.TaskResponseDTO;
import com.ifsp.task.service.TaskService;

@Validated
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping
  public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "priority") String sortBy) {
    Page<TaskResponseDTO> tasks = taskService.getAllTasks(page, size, sortBy);
    if (tasks.isEmpty())
      return ResponseEntity.noContent().build();
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
    TaskResponseDTO task = taskService.getTaskById(id);
    return ResponseEntity.ok(task);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<TaskResponseDTO>> getTasksByCategory(@RequestParam String category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "priority") String sortBy) {
    return ResponseEntity.ok(taskService.getTasksByCategory(category, page, size, sortBy));

  }

  @PostMapping
  public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO dto) {
    return ResponseEntity.ok(taskService.createTask(dto));
  }

  @PatchMapping("/{id}/concluir")
  public ResponseEntity<TaskResponseDTO> concludeTask(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.concludeTask(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO dto) {
    return ResponseEntity.ok(taskService.updateTask(id, dto));
  }

}
