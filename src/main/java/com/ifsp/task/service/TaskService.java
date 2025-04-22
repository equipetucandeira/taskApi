package com.ifsp.task.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsp.task.dto.TaskRequestDTO;
import com.ifsp.task.dto.TaskResponseDTO;
import com.ifsp.task.exception.ResourceNotFoundException;
import com.ifsp.task.model.Task;
import com.ifsp.task.repository.TaskRepository;
@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public List<TaskResponseDTO> getAllTasks(){
		List<Task> tasks = taskRepository.findAll();
		if (tasks.isEmpty()) return Collections.emptyList();
        return tasks.stream().map(TaskResponseDTO::new).toList();
	}
	
	public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        return new TaskResponseDTO(task);
    }
	
	public List<TaskResponseDTO> getTasksByCategory(String category){
		List<Task> tasks = taskRepository.findByCategory(category);
		if (tasks.isEmpty()) return Collections.emptyList();
		List<TaskResponseDTO> response = tasks.stream().map(TaskResponseDTO::new).collect(Collectors.toList());
		return response;
	}
	
	public TaskResponseDTO createTask(TaskRequestDTO dto) {
		Task task = taskRepository.save(dto.transformToObject());
		return new TaskResponseDTO(task);
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
		} else {
			task.setTitle(dto.getTitle());
			task.setDescription(dto.getDescription());
			task.setPriority(dto.getPriority());
			task.setLimitDate(dto.getLimitDate());
			task.setCategory(dto.getCategory());
			taskRepository.save(task);
			return new TaskResponseDTO(task);

		}
	}
	
	
	
	

}
