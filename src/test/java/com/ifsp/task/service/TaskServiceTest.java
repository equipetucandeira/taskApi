
package com.ifsp.task.service;

import com.ifsp.task.dto.TaskRequestDTO;
import com.ifsp.task.dto.TaskResponseDTO;
import com.ifsp.task.exception.BadRequestException;
import com.ifsp.task.model.PriorityType;
import com.ifsp.task.model.Task;
import com.ifsp.task.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*; // assertNotNull, assertEquals
import static org.mockito.ArgumentMatchers.any; // any(...)
import static org.mockito.Mockito.*; // when(), verify(), times()

@SpringBootTest
class TaskServiceTest {

  @MockBean
  private TaskRepository taskRepository;

  @MockBean
  private ModelMapper modelMapper;

  @Autowired
  private TaskService taskService;

  private TaskRequestDTO dto;

  @BeforeEach
  void setUp() {
    dto = new TaskRequestDTO(
        "Teste",
        "Descrição de teste",
        PriorityType.LOW,
        LocalDate.now().plusDays(2),
        "Daily");
  }

  @Test
  void createACorrectTask() {
    Task entity = new Task("Teste", "Descrição de teste",
        PriorityType.LOW, LocalDate.now().plusDays(2), "Daily");

    when(modelMapper.map(dto, Task.class)).thenReturn(entity);
    when(modelMapper.map(entity, TaskResponseDTO.class))
        .thenReturn(new TaskResponseDTO(entity));
    when(taskRepository.save(any(Task.class))).thenReturn(entity);

    TaskResponseDTO res = taskService.createTask(dto);

    assertNotNull(res);
    assertEquals("Teste", res.getTitle());
    verify(taskRepository, times(1)).save(any(Task.class));
  }

  @Test
  void createTaskWithInvalidLimitDate() {
    TaskRequestDTO dto = new TaskRequestDTO("Teste-falho", "Descrição de teste-falho",
        PriorityType.LOW, LocalDate.now().minusDays(2), "Daily");

    assertThrows(BadRequestException.class,
        () -> taskService.createTask(dto));

    verify(taskRepository, never()).save(any());
  }

  @Test
  void getTaskByIdAndReturnDto() {
    Task task = new Task("task", "Description",
        PriorityType.MEDIUM, LocalDate.now().plusDays(5), "Daily");
    task.setId(99L);

    when(taskRepository.findById(99L)).thenReturn(java.util.Optional.of(task));
    when(modelMapper.map(task, TaskResponseDTO.class))
        .thenReturn(new TaskResponseDTO(task));

    TaskResponseDTO dto = taskService.getTaskById(99L);

    assertEquals(99L, dto.getId());
    verify(taskRepository).findById(99L);
  }

  @Test
  void deleteCompletedTaskAndThrows409(){
    Task doneTask = new Task("DoneTask", "Descrip", PriorityType.HIGH, LocalDate.now().plusDays(1), "Daily");
    doneTask.setId(7L);
    doneTask.setComplete(true);

    when(taskRepository.findById(7L)).thenReturn(java.util.Optional.of(doneTask));
    assertThrows(IllegalStateException.class, () -> taskService.deleteTask(7L));

    verify(taskRepository, never()).delete(any());



  }


}
