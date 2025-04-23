package com.ifsp.task.dto;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ifsp.task.model.PriorityType;
import com.ifsp.task.model.Task;

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
  private LocalDate limitDate;
  private boolean isComplete;
  private String category;
  private LocalDate createdAt;

  
  public TaskResponseDTO(Task task) {
	    this.id = task.getId();
	    this.title = task.getTitle();
	    this.description = task.getDescription();
	    this.priority = task.getPriority();
	    this.limitDate = task.getLimitDate();
	    this.isComplete = task.isComplete();
	    this.category = task.getCategory();
	    this.createdAt = task.getCreatedAt();
	}
}
