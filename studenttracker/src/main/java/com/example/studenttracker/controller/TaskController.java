package com.example.studenttracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studenttracker.dto.TaskCreateRequest;
import com.example.studenttracker.dto.TaskUpdateRequest;
import com.example.studenttracker.entity.Task;
import com.example.studenttracker.repository.UserRepository;
import com.example.studenttracker.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get tasks by studentNumber
    @GetMapping("/student/{studentNumber}")
    public List<Task> getTasksByStudent(@PathVariable("studentNumber") String studentNumber) {
        return taskService.getTasksByStudentNumber(studentNumber);
    }

    // Create task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest request) {

        System.out.println("Received createTask request: " + request);

        if (request.getStudentNumber() == null || request.getStudentNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("student_number is required.");
        }

        if (!userRepository.existsByStudentNumber(request.getStudentNumber())) {
            return ResponseEntity.badRequest()
                    .body("User not found with student_number: " + request.getStudentNumber());
        }

        Task task = new Task();
        task.setStudentNumber(request.getStudentNumber());
        task.setSubject(request.getSubject());
        task.setNotes(request.getNotes());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriorityLevel(request.getPriorityLevel());

        Task savedTask = taskService.createTask(task);

        return ResponseEntity.ok(savedTask);
    }

    // Update task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskUpdateRequest request) {
        Task task = taskService.getTaskById(id);

        task.setSubject(request.getSubject());
        task.setNotes(request.getNotes());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriorityLevel(request.getPriorityLevel());

        return taskService.updateTask(task);
    }

    // Delete task
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully.";
    }
}
