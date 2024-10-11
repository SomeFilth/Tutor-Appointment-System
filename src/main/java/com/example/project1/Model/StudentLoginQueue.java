package com.example.project1.Model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

public class StudentLoginQueue implements Serializable {
    private LinkedList<StudentLogin> studentQueue;
    private StudentLogin currentStudent;

    public StudentLoginQueue() {
        studentQueue = new LinkedList<>();
        currentStudent = null;
    }

    public void loginStudent(StudentLogin studentLogin) {
        LocalDate currentDate = LocalDate.now();
        if (isToday(studentLogin.getDate(), currentDate)) {
            studentLogin.setDate(currentDate);
            studentLogin.setTimeIn(LocalDateTime.now());
            studentLogin.setTimeIn(null);

            if (currentStudent == null) {
                currentStudent = studentLogin;
                currentStudent.setTimeIn(LocalDateTime.now());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Login Successful");
                alert.setContentText("Enjoy your tutoring session!");
            }

            studentQueue.add(studentLogin);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Login Successful");
            alert.setContentText("You have successfully logged in!\nYou are number " + studentQueue.size() + " in the queue.");
            alert.showAndWait();
        }
    }

    public void logoutStudent(StudentLogin studentLogin) {

        LocalDate currentDate = LocalDate.now();
        if (isStudentLoggedIn(studentLogin) && isToday(studentLogin.getDate(), currentDate)) {
            studentLogin.setTimeOut(LocalDateTime.now());
            studentQueue.remove(studentLogin);
            System.out.println(studentLogin.getStudent().getName() + " has logged out.");
            currentStudent = studentQueue.isEmpty() ? null : studentQueue.getFirst();
            currentStudent.setTimeIn(LocalDateTime.now());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Logout Successful");
            alert.setContentText("You have successfully logged out!");
            alert.showAndWait();

            if (studentQueue.isEmpty()) {
                currentStudent = null;
            } else {
                currentStudent = studentQueue.getFirst();
            }
        }
    }

    public void logoutAllStudents() {
        LocalDate currentDate = LocalDate.now();
        studentQueue.removeIf(studentLogin -> isToday(studentLogin.getDate(), currentDate));
        currentStudent = studentQueue.isEmpty() ? null : studentQueue.getFirst();
    }

    public StudentLogin getCurrentStudent() {
        return currentStudent;
    }

    public LinkedList<StudentLogin> getStudentQueue() {
        return studentQueue;
    }

    public Optional<StudentLogin> findStudentById(String id){
        for(StudentLogin currentStudent : studentQueue){
            if(currentStudent.getStudent().getId().equals(id)){
                return Optional.of(currentStudent);
            }
        }
        return Optional.empty();
    }

    public Optional<StudentLogin> findStudentByName(String lastName, String firstName){
        String first;
        String last;
        String split = " ";

        for(StudentLogin currentStudent : studentQueue){
            last = currentStudent.getStudent().getName().split(split)[0];
            first = currentStudent.getStudent().getName().split(split)[1];
            if(last.equalsIgnoreCase(lastName) && first.equalsIgnoreCase(firstName)){
                return Optional.of(currentStudent);
            }
        }
        return Optional.empty();
    }


    public boolean isStudentLoggedIn(StudentLogin studentLogin) {
        return studentQueue.contains(studentLogin);
    }

    public boolean isEmpty() {
        return studentQueue.isEmpty();
    }

    public int size() {
        return studentQueue.size();
    }

    private boolean isToday(LocalDate date, LocalDate currentDate) {
        return date.isEqual(currentDate);
    }


    public void displayQueue(){
        for(StudentLogin studentLogin : studentQueue){
            System.out.println(studentLogin);
        }
    }

    public LinkedList<StudentLogin> getStudentLogins() {
        return studentQueue;
    }

    public StudentLogin getNextStudent() {
        return studentQueue.getFirst();
    }

    public void removeStudent(StudentLogin studentLogin) {
        studentQueue.remove(studentLogin);
    }




}
