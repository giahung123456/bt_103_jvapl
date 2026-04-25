package com.example.bt_10_3.repository;

import com.example.bt_10_3.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}