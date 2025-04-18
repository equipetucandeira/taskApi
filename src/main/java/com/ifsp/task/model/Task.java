package com.ifsp.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import com.ifsp.task.model.PriorityType;


@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private PriorityType priority;
  private Date limitDate;
  private boolean isComplete;
  private String category;
  @CreationTimestamp
  private LocalDateTime createdAt;


  public Task() {
  }

  public Task(String title, String description, PriorityType priorityType, Date limitDate,
      String category, Date createdAt) {
    this.title = title;
    this.description = description;
    this.priority = priorityType;
    this.limitDate = limitDate;
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

  public Date getLimitDate() {
    return limitDate;
  }

  public void setLimitDate(Date limitDate) {
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
