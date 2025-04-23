package com.ifsp.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ifsp.task.model.PriorityType;

@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "Title is required")
  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "Priority is required")
  private PriorityType priority;
  @Column(nullable = false)
  private LocalDate limitDate;
  private boolean isComplete;
  @NotBlank(message = "Category is required")
  private String category;
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDate createdAt;

  public Task() {
  }

  public Task(String title, String description, PriorityType priorityType, LocalDate limitDate,
      String category) {
    this.title = title;
    this.description = description;
    this.priority = priorityType;
    this.limitDate = limitDate;
    this.category = category;
    this.isComplete = false;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PriorityType getPriority() {
    return priority;
  }

  public void setPriority(PriorityType priority) {
    this.priority = priority;
  }

  public LocalDate getLimitDate() {
    return limitDate;
  }

  public void setLimitDate(LocalDate limitDate) {
    this.limitDate = limitDate;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public void setComplete(boolean isComplete) {
    this.isComplete = isComplete;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

}
