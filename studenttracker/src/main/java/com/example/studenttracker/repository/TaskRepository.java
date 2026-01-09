package com.example.studenttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studenttracker.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStudentNumber(String studentNumber);
}
