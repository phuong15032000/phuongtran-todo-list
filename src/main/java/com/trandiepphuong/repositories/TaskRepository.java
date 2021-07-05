package com.trandiepphuong.repositories;

import com.trandiepphuong.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll();
    List<Task> findByContentContaining(String content);
    Optional<Task> findById(int id);
}
