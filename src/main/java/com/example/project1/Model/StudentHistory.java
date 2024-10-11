package com.example.project1.Model;

import com.example.project1.Util.DataCenter;

import java.io.Serializable;
import java.util.LinkedList;

public class StudentHistory implements Serializable {
    private LinkedList<StudentLogin> studentHistory;

    public StudentHistory(){
        studentHistory = new LinkedList<StudentLogin>();
    }

    public void addStudent(StudentLogin student){
        System.out.println("Student added to history");
        studentHistory.add(student);
        DataCenter.getInstance().saveHistory();
    }

    public void removeStudent(StudentLogin student){
        studentHistory.remove(student);
    }

    public LinkedList<StudentLogin> getStudentHistory(){
        return studentHistory;
    }

    public void setStudentHistory(LinkedList<StudentLogin> studentHistory){
        this.studentHistory = studentHistory;
    }

    public String displayHistory(){
        String history = "";
        for(StudentLogin student : studentHistory){
            history += student.getStudent().getName() + "\n";
        }
        return history;
    }

    public void filterHistory(){
        for(StudentLogin student : studentHistory){
            if(student.getTimeIn() == null){
                studentHistory.remove(student);
            }
        }
        System.out.println("History filtered");
    }
}
