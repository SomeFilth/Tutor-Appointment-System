package com.example.project1.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Student implements Serializable {

    private String id;
    private String name;
    private String course;

    public Student(String id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }


    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(course, student.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, course);
    }



}
