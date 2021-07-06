package com.trandiepphuong.todolist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trandiepphuong.controllers.TaskController;
import com.trandiepphuong.models.Task;
import com.trandiepphuong.services.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Mock
    private TaskService taskService;
    private Task task;
    private List<Task> taskList;

    @InjectMocks
    private TaskController taskController;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        task = new Task(1, "do homework", "Done");
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @AfterEach
    void tearDown() {
        task = null;
    }

    @Test
    public void PostMapping() throws Exception {
        when(taskService.save(any())).thenReturn(task);
        mockMvc.perform(post("/api/tasks").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(task))).
                andExpect(status().isCreated());
    }

    @Test
    public void GetMappingOfAllTask() throws Exception {
        when(taskService.findAll()).thenReturn(taskList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(task))).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetProductById() throws Exception {
        when(taskService.findById(task.getId())).thenReturn(java.util.Optional.ofNullable(task));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(task))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1000").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isNotFound()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteById() throws Exception {
        when(taskService.deleteById(task.getId())).thenReturn(task);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetTaskByContent() throws Exception {
        List<Task> temp = new ArrayList<>();
        temp.add(task);
        when(taskService.findByContentContaining("do")).thenReturn(temp);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/search?keyword=do").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(task))).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateTask() throws Exception {
        task.setContent("Eat dinner");

        when(taskService.update(task.getId(),task)).thenReturn(task);
        String json = asJsonString(task);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
