package com.example.project1.Model;

import com.example.project1.Util.DataCenter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Optional;

public class StudentLinkList implements Serializable {
        private LinkedList<Student> studentList;


        public StudentLinkList(){
            studentList = new LinkedList<Student>();
        }

        public void addStudent(Student student){
            studentList.add(student);
        }

        public Optional<Student> findStudentById(String id){
            for(Student student : studentList){
                if(student.getId().equals(id)){
                    return Optional.of(student);
                }
            }
            return Optional.empty();
        }

        public Optional<Student> findStudentByName(String lastName, String firstName){
            String first;
            String last;
            String split = " ";

            for(Student student : studentList){
                last = student.getName().split(split)[0];
                first = student.getName().split(split)[1];
                if(last.equalsIgnoreCase(lastName) && first.equalsIgnoreCase(firstName)){
                    return Optional.of(student);
                }
            }
            return Optional.empty();
        }


}

