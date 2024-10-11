package com.example.project1.Controller;

import com.example.project1.Model.StudentLogin;
import com.example.project1.Model.StudentLoginQueue;
import com.example.project1.Util.DataCenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class QueueController implements Initializable {


    @FXML
    private Button start, end;

    @FXML
    private ListView<String> nameView;

    @FXML
    public TableView<StudentLogin> studentTable;
    @FXML
    private TableColumn<StudentLogin, String> nameColumn, courseColumn, courseHelpColumn;

    private final ObservableList<StudentLogin> selectedStudents = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        DataCenter dataCenter = DataCenter.getInstance();
        queueList();
        setupStudentTableClickHandling();
    }

    private void queueList() {
        studentTable.getItems().clear();

        StudentLoginQueue studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        LinkedList<StudentLogin> studentLogins = studentLoginQueue.getStudentLogins();

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getName()));
        courseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getCourse()));
        courseHelpColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseHelp()));

        studentTable.getItems().addAll(studentLogins);
    }

    @FXML
    public void startButtonOnAction() {
        StartTheTimeIn();
    }

    @FXML
    public void EndButtonOnAction() {
        logOutStop();
        removeStudentsFromQueueAndRefreshTableView();
        queueList();
        selectedStudents.clear();
        updateNameView();
    }

    private void setupStudentTableClickHandling() {
        studentTable.setRowFactory(tv -> {
            TableRow<StudentLogin> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    StudentLogin selectedStudent = row.getItem();
                    if (selectedStudents.contains(selectedStudent)) {
                        selectedStudents.remove(selectedStudent);
                    } else {
                        selectedStudents.add(selectedStudent);
                    }
                    updateNameView();
                }
            });
            return row;
        });
    }

    private void updateNameView() {
        nameView.getItems().clear();
        for (StudentLogin student : selectedStudents) {
            nameView.getItems().add(student.getStudent().getName());
        }
    }

    private void removeStudentsFromQueueAndRefreshTableView() {
        StudentLoginQueue studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        ObservableList<String> selectedNames = nameView.getItems();

        LinkedList<StudentLogin> updatedStudentQueue = new LinkedList<>();

        for (StudentLogin studentLogin : studentLoginQueue.getStudentLogins()) {
            String fullName = studentLogin.getStudent().getName();
            if (!selectedNames.contains(fullName)) {
                updatedStudentQueue.add(studentLogin);
            }

        }

        studentLoginQueue.getStudentLogins().clear();
        studentLoginQueue.getStudentLogins().addAll(updatedStudentQueue);
    }

    private void StartTheTimeIn(){
        StudentLoginQueue studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        ObservableList<String> selectedNames = nameView.getItems();

        LinkedList<StudentLogin> updatedStudentQueue = new LinkedList<>();

        for (StudentLogin studentLogin : studentLoginQueue.getStudentLogins()) {
            String fullName = studentLogin.getStudent().getName();
            if (selectedNames.contains(fullName)) {
                studentLogin.setTimeIn(LocalDateTime.now());
                updatedStudentQueue.add(studentLogin);
            }
        }
        studentLoginQueue.displayQueue();
    }

    private void logOutStop(){
        StudentLoginQueue studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        ObservableList<String> selectedNames = nameView.getItems();

        LinkedList<StudentLogin> updatedStudentQueue = new LinkedList<>();

        for (StudentLogin studentLogin : studentLoginQueue.getStudentLogins()) {
            String fullName = studentLogin.getStudent().getName();
            if (selectedNames.contains(fullName)) {
                studentLogin.setTimeOut(LocalDateTime.now());
                updatedStudentQueue.add(studentLogin);
                DataCenter.getInstance().getStudentHistory().addStudent(studentLogin);
            }
        }
        studentLoginQueue.displayQueue();
    }
}
