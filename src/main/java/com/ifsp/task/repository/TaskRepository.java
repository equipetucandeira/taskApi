package com.ifsp.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsp.task.model.Task;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findByCategory(String category, Pageable pageable);
  Page<Task> findAll(Pageable pageable);

}
