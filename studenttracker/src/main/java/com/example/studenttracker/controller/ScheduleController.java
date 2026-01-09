package com.example.studenttracker.controller;

import java.time.LocalTime;
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

import com.example.studenttracker.dto.ScheduleCreateRequest;
import com.example.studenttracker.dto.ScheduleUpdateRequest;
import com.example.studenttracker.entity.Schedule;
import com.example.studenttracker.repository.UserRepository;
import com.example.studenttracker.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserRepository userRepository;

    // Get all schedules
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // Get schedules by studentNumber
    @GetMapping("/student/{studentNumber}")
    public List<Schedule> getSchedulesByStudent(@PathVariable String studentNumber) {
        return scheduleService.getSchedulesByStudentNumber(studentNumber);
    }

    // Create schedule
    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleCreateRequest request) {

        if (request.getStudentNumber() == null || request.getStudentNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("student_number is required.");
        }

        if (!userRepository.existsByStudentNumber(request.getStudentNumber())) {
            return ResponseEntity.badRequest()
                    .body("User not found with student_number: " + request.getStudentNumber());
        }

        Schedule schedule = new Schedule();
        schedule.setStudentNumber(request.getStudentNumber());
        schedule.setSubject(request.getSubject());
        schedule.setRoom(request.getRoom());
        schedule.setDay(request.getDay());
        schedule.setStartTime(LocalTime.parse(request.getStartTime()));
        schedule.setEndTime(LocalTime.parse(request.getEndTime()));
        schedule.setTeacherName(request.getTeacherName()); // added

        Schedule savedSchedule = scheduleService.createSchedule(schedule);

        return ResponseEntity.ok(savedSchedule);
    }

    // Update schedule
    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateRequest request) {
        Schedule schedule = scheduleService.getScheduleById(id);

        schedule.setSubject(request.getSubject());
        schedule.setRoom(request.getRoom());
        schedule.setDay(request.getDay());
        schedule.setStartTime(LocalTime.parse(request.getStartTime()));
        schedule.setEndTime(LocalTime.parse(request.getEndTime()));
        schedule.setTeacherName(request.getTeacherName()); // added

        return scheduleService.updateSchedule(schedule);
    }

    // Delete schedule
    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "Schedule deleted successfully.";
    }
}
