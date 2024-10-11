package com.example.project1.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class StudentQueue implements Serializable {
    private LocalDateTime time;
    private LinkedList<Student> studentqueue;

    public StudentQueue(){
        studentqueue = new LinkedList<Student>();
    }

    public void addStudent(Student student){
        studentqueue.add(student);
    }

    public void removeStudent(Student student){
        studentqueue.remove(student);
    }

    public void setTime(LocalDateTime time){
        this.time = time;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public LinkedList<Student> getStudentQueue(){
        return studentqueue;
    }

    public void setStudentQueue(LinkedList<Student> studentqueue){
        this.studentqueue = studentqueue;
    }

    public void clearQueue(){
        studentqueue.clear();
    }

    public void peek(){
        System.out.println(studentqueue.peek());
    }


}
