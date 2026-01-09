package com.example.studenttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studenttracker.entity.User;
import com.example.studenttracker.exception.ResourceNotFoundException;
import com.example.studenttracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ===== CREATE / SAVE USER =====
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ===== GET ALL USERS =====
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ===== GET USER BY ID =====
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    // ===== GET USER BY STUDENT NUMBER =====
    public User getUserByStudentNumber(String studentNumber) {
        return userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with student number " + studentNumber));
    }

    // ===== UPDATE USER =====
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setStudentNumber(updatedUser.getStudentNumber());
        return userRepository.save(existingUser);
    }

    // ===== DELETE USER =====
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // ===== FIND USER BY EMAIL (for login) =====
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
    }

    // ===== CHECK IF EMAIL EXISTS =====
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // ===== CHECK IF STUDENT NUMBER EXISTS =====
    public boolean existsByStudentNumber(String studentNumber) {
        return userRepository.existsByStudentNumber(studentNumber);
    }

    // ===== GET CURRENT LOGGED-IN USER (FOR TESTING PURPOSES) =====
    public User getCurrentLoggedInUser() {
        // For testing only: hardcode user ID 1
        // Replace with real authentication logic later
        return getUserById(1L);
    }
}
