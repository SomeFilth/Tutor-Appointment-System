package com.example.project1.Util;

import com.example.project1.Model.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataCenter implements Serializable {

    public static DataCenter instance = null;

    private StudentLinkList studentList = new StudentLinkList();

    private StudentLoginQueue studentLoginQueue = new StudentLoginQueue();
    private StudentHistory studentHistory = new StudentHistory();


    private Student student;
    private File file;

    private DataCenter() {
        file = new File("project1fall2023 - project1fall2023");
        loadStudents();
        loadHistory();
    }

    public static DataCenter getInstance() {
        if (instance == null) {
            instance = new DataCenter();
        }
        return instance;
    }


    public Student currentStudent(){
        return student;
    }

    public void setCurrentStudent(Student student){
        this.student = student;
    }

    public void insertStudent(String id, String name, String course){
        Student newStudent = new Student(id, name, course);
        studentList.addStudent(newStudent);
        saveStudents(newStudent);
    }

    public void saveStudents(Student student){
        try{
            CSVWriter.writeCSV("src/main/Data/" + file + ".csv", student);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadStudents(){
        try{
            studentList = CSVReader.readCSV("src/main/Data/"+file+".csv");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveHistory(){
        try(ObjectOutputStream historyOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/main/BackUp/"+file+".dat")))){
            studentHistory.filterHistory();
            System.out.println(studentHistory.getStudentHistory().size() + " students in history");
            historyOut.writeObject(studentHistory);
    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadHistory(){
        try(ObjectInputStream historyInput = new ObjectInputStream(new BufferedInputStream(new FileInputStream("src/main/BackUp/"+file+".dat")))){
            studentHistory = (StudentHistory) historyInput.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StudentLogin(Student student,String instructor, String courseHelp, LocalDateTime timeIn, LocalDateTime timeOut, LocalDate date){
        StudentLogin newStudentLogin = new StudentLogin(student, instructor, courseHelp, timeIn, timeOut, date);

    }

    public StudentLoginQueue getStudentLoginQueue() {
        return studentLoginQueue;
    }

    public StudentHistory getStudentHistory() {
        return studentHistory;
    }

    public void setStudentHistory(StudentHistory studentHistory) {
        this.studentHistory = studentHistory;
    }

    public StudentLinkList getStudentLinkList() {
        return studentList;
    }


    public void importData(String filename) {
        this.file = new File(filename);
    }

    public String getFileName() {
        return file.getName();
    }
}
