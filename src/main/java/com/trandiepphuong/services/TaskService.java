package com.trandiepphuong.services;

import com.trandiepphuong.models.Task;
import com.trandiepphuong.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    public Task save(Task task) {
        return taskRepository.save(task);
    }
    public List<Task> findAll(){
        return (List<Task>) taskRepository.findAll();
    }
    public List<Task> findByContentContaining(String content){
        return (List<Task>) taskRepository.findByContentContaining(content);
    }
    public Optional<Task> findById(int id){
        return (Optional<Task>) taskRepository.findById(id);
    }

    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }
}
