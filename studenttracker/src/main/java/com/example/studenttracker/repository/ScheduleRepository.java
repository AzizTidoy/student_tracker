package com.example.studenttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studenttracker.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStudentNumber(String studentNumber);
}
