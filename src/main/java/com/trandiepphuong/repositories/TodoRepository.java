package com.trandiepphuong.repositories;

import com.trandiepphuong.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Optional<Todo> findByContent(int id);

    List<Todo> findByContentLike(String content);

    List<Todo> findByContentContaining(String content);
}
