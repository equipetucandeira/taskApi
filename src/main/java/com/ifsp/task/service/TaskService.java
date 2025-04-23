package com.ifsp.task.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.ifsp.task.dto.TaskRequestDTO;
import com.ifsp.task.dto.TaskResponseDTO;
import com.ifsp.task.exception.BadRequestException;
import com.ifsp.task.exception.ResourceNotFoundException;
import com.ifsp.task.model.Task;
import com.ifsp.task.repository.TaskRepository;

import jakarta.validation.ConstraintViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ifsp.task.config.MapperConfig;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ModelMapper model;

  public Page<TaskResponseDTO> getAllTasks(int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    Page<Task> tasks = taskRepository.findAll(pageable);
    if (tasks == null) return Page.empty(pageable);
    return tasks.map(TaskResponseDTO::new);
  }

  public TaskResponseDTO getTaskById(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    return new TaskResponseDTO(task);
  }

  public Page<TaskResponseDTO> getTasksByCategory(String category, int page, int pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).ascending());
    Page<Task> tasks = taskRepository.findByCategory(category, pageable);
    return tasks.map(TaskResponseDTO::new);
  }

  public TaskResponseDTO createTask(TaskRequestDTO dto) {
    if (dto.getLimitDate().isBefore(LocalDate.now())) {
      throw new BadRequestException("A data limite não pode ser anterior à data atual.");
    }
    Task task = model.map(dto, Task.class);
    taskRepository.save(task);
    return model.map(task, TaskResponseDTO.class);
  }

  public TaskResponseDTO concludeTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    if (task.isComplete()) {
      throw new IllegalStateException("Tarefa já foi concluída.");
    } else {
      task.setComplete(true);
      taskRepository.save(task);
      return new TaskResponseDTO(task);
    }
  }

  public boolean deleteTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
    if (task.isComplete()) {
      throw new IllegalStateException("Tarefa concluída não pode ser removida.");
    }
    taskRepository.delete(task);
    return true;
  }

  public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

    if (task.isComplete()) {
      throw new IllegalStateException("Tarefa já foi concluída.");
    }

    model.map(dto, task);

    Task updated = taskRepository.save(task);
    return new TaskResponseDTO(updated);
  }

}
