package com.example.project1.Controller;


import com.example.project1.Model.Student;
import com.example.project1.Model.StudentLogin;
import com.example.project1.Model.StudentLoginQueue;
import com.example.project1.Util.DataCenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StudentLogViewController implements Initializable {

    private Student currentStudent;
    private StudentLogin currentStudentLogin;
    private StudentLoginQueue studentLoginQueue;

    @FXML
    private TextField name, id;
    @FXML
    private Button Login, Return;
    @FXML
    private ChoiceBox<String> instructorChoiceBox;
    private String[] instructors = {"Bin Li", "Xingbin Chen", "William McAllister"};
    @FXML
    private TextArea helpInfo;
    @FXML
    private ListView<String> courseList;
    private DataCenter dataCenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataCenter dataCenter = DataCenter.getInstance();
        currentStudent = dataCenter.currentStudent();
        Login();
        displayCourse();
        instructorChoiceBox.setValue("Instructors");
        instructorChoiceBox.getItems().addAll(instructors);
    }

    @FXML
    public void LoginButtonOnAction(ActionEvent event)throws Exception{
        if(allFilledIn()) {
            dataCenter = DataCenter.getInstance();
            currentStudent = dataCenter.currentStudent();
            currentStudent.setCourse(courseList.getSelectionModel().getSelectedItem());
            currentStudentLogin = new StudentLogin(currentStudent, instructorChoiceBox.getValue(), helpInfo.getText(), LocalDateTime.now(), null, LocalDate.now());
            studentLoginQueue = dataCenter.getStudentLoginQueue();
            studentLoginQueue.loginStudent(currentStudentLogin);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/Main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        }
    }



    @FXML
    public void returnButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/Main.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayCourse() {
        DataCenter dataCenter = DataCenter.getInstance();
        currentStudent = dataCenter.currentStudent();
        String[] courses = currentStudent.getCourse().split("\\s+");
        ObservableList<String> courseList = FXCollections.observableArrayList();
        courseList.addAll(courses);
        this.courseList.setItems(courseList);
    }

    public void Login() {
        name.setDisable(true);
        id.setDisable(true);
        name.setText(currentStudent.getName());
        id.setText(currentStudent.getId());
    }

    public Boolean allFilledIn(){
        if(instructorChoiceBox.getValue().equals("Instructors") || helpInfo.getText().isBlank()|| courseList.getSelectionModel().isEmpty()){
            return false;
        }
        return true;
    }
}

