package com.trandiepphuong.services;

import com.trandiepphuong.models.Task;
import com.trandiepphuong.repositories.TaskRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByContentContaining(String content) {
        return taskRepository.findByContentContaining(content);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Task update(int id, Task task) throws NotFoundException {
        Optional<Task> todoData = taskRepository.findById(id);
        if (todoData.isPresent()) {
            Task _task = todoData.get();
            _task.setContent(task.getContent());
            _task.setStatus(task.getStatus());
            return taskRepository.save(_task);
        }
        throw new NotFoundException("Not found exception");
    }
}
