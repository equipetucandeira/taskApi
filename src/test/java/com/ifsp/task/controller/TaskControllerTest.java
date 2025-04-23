package com.ifsp.task.controller;

import com.ifsp.task.dto.*;
import com.ifsp.task.model.*;
import com.ifsp.task.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;

import com.ifsp.task.service.TaskService;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskService taskService;

  @Test

  void listPagedTasksandReturn() throws Exception {

    Task task1 = new Task("Tirar o lixo", "Precisa retirar o lixo do banheiro",
        PriorityType.LOW,
        LocalDate.now().plusDays(1), "Daily");
    task1.setId(1L);
    task1.setCreatedAt(LocalDate.now());
    TaskResponseDTO dto1 = new TaskResponseDTO(task1);

    Task task2 = new Task("Tirar o lixo 2",
        "Precisa retirar o lixo do banheiro 2", PriorityType.LOW,
        LocalDate.now().plusDays(2), "Daily");
    task2.setId(2L);
    task2.setCreatedAt(LocalDate.now());
    TaskResponseDTO dto2 = new TaskResponseDTO(task2);

    Page<TaskResponseDTO> page = new PageImpl<>(List.of(dto1, dto2),
        PageRequest.of(0, 2), 2);

    when(taskService.getAllTasks(eq(0), eq(2), eq("priority")))
        .thenReturn(page);

    mockMvc.perform(get("/api/tasks")
        .param("page", "0")
        .param("size", "2")
        .param("sortBy", "priority"))
        .andDo(print()) // <‑‑ imprime body/erro
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(2))
        .andExpect(jsonPath("$.content[0].title").value("Tirar o lixo"))
        .andExpect(jsonPath("$.content[1].title").value("Tirar o lixo 2"));

  }

  @Test

  void searchTasksByCategory() throws Exception {
    Task task1 = new Task("Tirar o lixo", "Precisa retirar o lixo do banheiro",
        PriorityType.LOW,
        LocalDate.now().plusDays(1), "Daily");
    task1.setId(1L);
    task1.setCreatedAt(LocalDate.now());
    TaskResponseDTO dto1 = new TaskResponseDTO(task1);

    Task task2 = new Task("Tirar o lixo 2", "Precisa retirar o lixo do banheiro",
        PriorityType.LOW,
        LocalDate.now().plusDays(1), "Daily");
    task1.setId(2L);
    task1.setCreatedAt(LocalDate.now());
    TaskResponseDTO dto2 = new TaskResponseDTO(task2);

    Page<TaskResponseDTO> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 2), 2);

    when(taskService.getTasksByCategory("Daily", 0, 2, "priority")).thenReturn(page);

    mockMvc.perform(get("/api/tasks/search?category=Daily&page=0&size=2&sortBy=priority"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.content.length()").value(2))
        .andExpect(jsonPath("$.content[0].title").value("Tirar o lixo"))
        .andExpect(jsonPath("$.content[1].title").value("Tirar o lixo 2"));
  }

}
