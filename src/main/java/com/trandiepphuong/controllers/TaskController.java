package com.trandiepphuong.controllers;

import com.trandiepphuong.models.Task;
import com.trandiepphuong.repositories.TaskRepository;
import com.trandiepphuong.services.TaskService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<Task>> getAll() {
        System.out.println("get all");
        List<Task> tasks = taskService.findAll();
        try {
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> getTodoByContent(@RequestParam String keyword) {
        System.out.println("get by content");
        List<Task> taskData = taskService.findByContentContaining(keyword);
        return new ResponseEntity<>(taskData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTodoById(@PathVariable int id) {
        System.out.println("get by id");
        Optional<Task> todoData = taskService.findById(id);
        if (!todoData.isEmpty()) {
            return new ResponseEntity<>(todoData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    ResponseEntity<Task> post(@RequestBody Task task) {
        try {
            if (task.getContent().equals("")){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Task _task = taskService.save(task);
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        try {
            taskService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> put(@RequestBody Task task, @PathVariable int id) {
        Optional<Task> todoData = taskService.findById(id);
        if (todoData.isPresent()) {
            Task _task = todoData.get();
            _task.setContent(task.getContent());
            _task.setStatus(task.getStatus());
            return new ResponseEntity<>(taskService.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}