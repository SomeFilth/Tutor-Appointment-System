package com.example.project1.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class StudentLogin implements Serializable {

    private Student student;
    private String instructor;
    private String courseHelp;
    private LocalDateTime timeIn, timeOut;
    private LocalDate date;

    public StudentLogin() {

    }

    public StudentLogin(Student student,String instructor, String courseHelp, LocalDateTime timeIn, LocalDateTime timeOut, LocalDate date) {
        this.student = student;
        this.instructor = instructor;
        this.courseHelp = courseHelp;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.date = date;
    }

    public Student getStudentLogin() {
        return student;
    }

    public void setStudentLogin(Student student) {
        this.student = student;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCourseHelp() {
        return courseHelp;
    }


    public void setCourseHelp(String courseHelp) {
        this.courseHelp = courseHelp;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }

    public LocalDate getDate() {
        return date;
    }

    public Student getStudent() {
        return student;
    }

    public void setHelpInfo(String help) {
        this.courseHelp = help;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StudentLogin{" +
                "student=" + student +
                ", courseHelp='" + courseHelp + '\'' +
                ", timeIn=" + timeIn +
                ", date=" + date +
                ",timeOut=" + timeOut + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentLogin)) return false;
        StudentLogin that = (StudentLogin) o;
        return Objects.equals(student.getId(), that.student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student.getId());
    }

    public String getHelpInfo() {
        return courseHelp;
    }
}
