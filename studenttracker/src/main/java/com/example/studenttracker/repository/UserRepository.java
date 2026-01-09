package com.example.studenttracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.studenttracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by studentNumber (add this!)
    Optional<User> findByStudentNumber(String studentNumber);

    // Check if email exists
    boolean existsByEmail(String email);

    // Check if studentNumber exists
    boolean existsByStudentNumber(String studentNumber);
}
