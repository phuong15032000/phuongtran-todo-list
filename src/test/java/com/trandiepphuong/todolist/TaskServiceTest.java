package com.trandiepphuong.todolist;

import com.trandiepphuong.models.Task;
import com.trandiepphuong.repositories.TaskRepository;
import com.trandiepphuong.services.TaskServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Autowired
    @InjectMocks
    private TaskServiceImpl taskService;
    private Task t1;
    private Task t2;
    List<Task> todolist;

    @BeforeEach
    public void setUp() {
        todolist = new ArrayList<>();
        t1 = new Task(1, "Do homework", "Done");
        t2 = new Task(2, "Play games", "Not yet");
        todolist.add(t1);
        todolist.add(t2);
    }

    @AfterEach
    public void tearDown() {
        t1 = t2 = null;
        todolist = null;
    }

    @Test
    void addTask() {
        when(taskRepository.save(any())).thenReturn(t1);
        taskService.save(t1);
    }

    @Test
    public void allTasks() {
        taskRepository.save(t1);
        //stubbing mock to return specific data
        when(taskRepository.findAll()).thenReturn(todolist);
        List<Task> taskList1 = taskService.findAll();
        assertEquals(taskList1, todolist);
    }

    @Test
    public void getTaskById() {
        when(taskRepository.findById(1)).thenReturn(Optional.ofNullable(t1));
        assertThat(taskService.findById(t1.getId()).get()).isEqualTo(t1);
    }

    @Test
    public void deleteById() {
        when(taskRepository.deleteById(t1.getId())).thenReturn(t1);
        assertThat(taskService.deleteById(t1.getId())).isEqualTo(t1);
    }

    @Test
    public void getTaskByContent() {
        taskRepository.save(t1);
        List<Task> temp = new ArrayList<>();
        temp.add(t1);
        when(taskRepository.findByContentContaining("do")).thenReturn(temp);
        List<Task> taskList1 = taskService.findByContentContaining("do");
        assertEquals(taskList1, temp);
    }
}
