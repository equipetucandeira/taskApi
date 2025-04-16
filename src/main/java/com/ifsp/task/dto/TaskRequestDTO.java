package com.ifsp.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifsp.task.model.PriorityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
  @NotBlank(message = "Title is required")
  private String title;
  private String description;

  @NotNull(message = "Priority must be LOW, MEDIUM or HIGH")
  private PriorityType priority;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date limitDate;
  private boolean isComplete;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;
}
