package com.kodillatask.rest_api_3.tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kodillatask.rest_api_3.tasks.domain.task.Task;
import com.kodillatask.rest_api_3.tasks.domain.task.TaskDto;
import com.kodillatask.rest_api_3.tasks.mapper.TaskMapper;
import com.kodillatask.rest_api_3.tasks.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService service;
    @MockBean
    private TaskMapper taskMapper;

    ObjectMapper mapper;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        TaskDto taskDto1 = new TaskDto(1L, "Test_title1", "Test_content1");
        TaskDto taskDto2 = new TaskDto(2L, "Test_title2", "Test_content2");
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto1);
        taskDtoList.add(taskDto2);

        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test_title1")))
                .andExpect(jsonPath("$[0].content", is("Test_content1")));
    }

    @Test
    public void shouldGetTask() throws Exception {
//        Task task = new Task(1L,"Test","Test content");
//        TaskDto taskDto = new TaskDto(1L,"Test","Test content");
//
//        when(service.findTaskById(task.getId())).thenReturn(java.util.Optional.of(task).get());
//        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
//
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(taskDto);
//
//        mockMvc.perform(get("/v1/task/{}", task.getId())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.title").value("Test"))
//                .andExpect(jsonPath("$.content").value("Test content"));
    }

    @Test
    public void shouldFetchEmptyTasksList() throws Exception {

        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDto = new ArrayList<>();

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDto);
        mockMvc.perform(get("/v1/task").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        Task task = new Task(1L,"Test","Test content");
        mockMvc.perform(delete("/v1/task/{id}", task.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateTask() throws Exception {

        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Updated title", "Updated content");

        when(service.findTaskById(task.getId())).thenReturn(java.util.Optional.of(task).get());
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(put("/v1/task/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateNewTask() throws Exception {

//        Task task = new Task();
//        TaskDto taskDto = new TaskDto(1L, "title", "content");
//
//        when(service.save(task)).thenReturn(java.util.Optional.of(task).get());
//        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
//
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(taskDto);
//
//        mockMvc.perform(post("/v1/task/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().isCreated());
    }
}
