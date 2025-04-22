package com.ifsp.task.controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.task.dto.TaskRequestDTO;
import com.ifsp.task.dto.TaskResponseDTO;
import com.ifsp.task.exception.ResourceNotFoundException;
import com.ifsp.task.model.Task;
import com.ifsp.task.repository.TaskRepository;

@Validated
@RestController
@RequestMapping("/api/tasks")
public class TaskController{
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> getAllTasks(){
		List<Task> tasks = taskRepository.findAll(); 
		if(tasks.isEmpty()) return ResponseEntity.noContent().build();
		List<TaskResponseDTO> response = tasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
	    return taskRepository.findById(id)
	        .map(task -> ResponseEntity.ok(new TaskResponseDTO(task)))
	        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
	}
	@GetMapping("/search")
	public ResponseEntity<List<TaskResponseDTO>> getTasksByCategory(@RequestParam("category") String category){
		List<Task> tasks = taskRepository.findByCategory(category);
		if(tasks.isEmpty()) return ResponseEntity.noContent().build();
		List<TaskResponseDTO> response = tasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
		
	}

	@PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO dto) {
        Task task = taskRepository.save(dto.transformToObject());
        return ResponseEntity.ok(new TaskResponseDTO(task));
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
	    Task task = taskRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

	    if (task.isComplete()) {
	        throw new IllegalStateException("Tarefa concluída não pode ser removida.");
	    }

	    taskRepository.delete(task);
	    return ResponseEntity.noContent().build();
	}
	
	
	
}