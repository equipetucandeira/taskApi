package com.ifsp.task.dto;

import java.util.Date;
import java.time.LocalDateTime;

import com.ifsp.task.model.PriorityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaskResponseDTO {
  private Long id;
  private String title;
  private String description;
  private PriorityType priority;
  private Date limitDate;
  private boolean isComplete;
  private String category;
  private LocalDateTime createdAt;

}
