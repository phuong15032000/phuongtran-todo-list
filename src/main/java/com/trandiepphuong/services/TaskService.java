package com.trandiepphuong.services;

import com.trandiepphuong.models.Task;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    public List<Task> findAll();

    List<Task> findByContentContaining(String content);

    Optional<Task> findById(int id);

    Task deleteById(int id);
}
