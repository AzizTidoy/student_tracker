package com.example.studenttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.studenttracker.dto.UserCreateRequest;
import com.example.studenttracker.entity.User;
import com.example.studenttracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    // -------------------- SIGNUP --------------------
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserCreateRequest request) {

        // Check if email already exists
        if(userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        // Check if student Number already exists
        if(userService.existsByStudentNumber(request.getStudentNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student Number already exists");
        }

        // Check if passwords match
        if(!request.getPassword().equals(request.getRePassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        // Create user entity
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setStudentNumber(request.getStudentNumber());
        user.setPassword(request.getPassword()); // In production, hash the password!

        // SAVE USING CORRECT SERVICE METHOD
        User createdUser = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // -------------------- LOGIN --------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.getUserByEmail(email);

            // In production, use password hashing instead of plain text comparison
            if(user.getPassword().equals(password)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
