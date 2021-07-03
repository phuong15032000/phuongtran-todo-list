package com.trandiepphuong.controllers;

import com.trandiepphuong.models.Todo;
import com.trandiepphuong.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> getAll() {
        System.out.println("get all");
        List<Todo> todos = todoRepository.findAll();
        try {
            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{content}")
    public ResponseEntity<List<Todo>> getTodoByContent(@PathVariable String content) {
        System.out.println("get by content");
        List<Todo> todoData = todoRepository.findByContentContaining(content);
        if (!todoData.isEmpty()) {
            return new ResponseEntity<>(todoData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/")
    public ResponseEntity<Optional<Todo>> getTodoById(@RequestParam int id) {
        System.out.println("get by id");
        Optional<Todo> todoData = todoRepository.findById(id);
        if (!todoData.isEmpty()) {
            return new ResponseEntity<>(todoData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping()
    ResponseEntity<Todo> post(@RequestBody Todo todo) {
        try {
            Todo _todo = todoRepository.save(todo);
            return new ResponseEntity<>(_todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping()
    ResponseEntity<HttpStatus> deleteAll() {
        try {
            todoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Todo> put(@RequestBody Todo todo) {
        System.out.println("update");
        Optional<Todo> todoData = todoRepository.findById(todo.getId());
        if (todoData.isPresent()) {
            Todo _todo = todoData.get();
            _todo.setContent(todo.getContent());
            return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}