package com.example.studenttracker.dto;

import com.example.studenttracker.entity.Schedule.Day;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleCreateRequest {

    private String studentNumber;
    private String subject;
    private String room;
    private Day day;
    private String teacherName; // added

    @JsonFormat(pattern = "HH:mm")
    private String startTime;

    @JsonFormat(pattern = "HH:mm")
    private String endTime;

    // Getters and setters
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}
