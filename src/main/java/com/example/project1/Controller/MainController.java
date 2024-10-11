package com.example.project1.Controller;

import com.example.project1.Model.*;
import com.example.project1.Util.DataCenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Student loggedOutStudent;
    private StudentLoginQueue studentLoginQueue;
    private StudentHistory studentHistory;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField sId;

    @FXML
    private Button loginButton, logOutButton;

    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private Hyperlink adminAndTutorLogin;
    @FXML
    private Hyperlink queueLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeStudentIDField();
        configureTextField(firstName);
        configureTextField(lastName);
    }

    @FXML
    private void loginButtonOnAction(ActionEvent event) throws Exception {

        String firstName = this.firstName.getText().trim(), lastName = this.lastName.getText().trim(), studentID = this.sId.getText();
        StudentLinkList studentLinkList = DataCenter.getInstance().getStudentLinkList();
        Optional<Student> loggingStudent = studentLinkList.findStudentByName(lastName, firstName), loggingStudentByID = studentLinkList.findStudentById(studentID);
        studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        Optional<StudentLogin> loggingStudentLogin = studentLoginQueue.findStudentById(studentID), loggingStudentLoginByName = studentLoginQueue.findStudentByName(lastName, firstName);
        if (loggingStudent.isPresent() || loggingStudentByID.isPresent()) {
            if (loggingStudentLogin.isPresent() || loggingStudentLoginByName.isPresent()) {
                studentLoginQueue.getNextStudent().setTimeIn(LocalDateTime.now());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Student already logged in");
                alert.setContentText("Please log out first");
                alert.showAndWait();
                return;
            }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/StudentLogView.fxml"));
                Student loginedIn = loggingStudent.orElseGet(loggingStudentByID::get);
                DataCenter.getInstance().setCurrentStudent(loginedIn);
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                System.out.println("Student found");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Student not found");
            alert.setContentText("Please enter a valid student name or student ID\nIf you're a new student, please create an account");
            alert.showAndWait();
        }

    }

    @FXML
    private void logOutButtonOnAction(ActionEvent event)  {
        try {
            String firstName = this.firstName.getText().trim(), lastName = this.lastName.getText().trim(), studentID = this.sId.getText();
            studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
            Optional<StudentLogin> loggingStudentLogin = studentLoginQueue.findStudentById(studentID);
            Optional<StudentLogin> loggingStudentLoginByName = studentLoginQueue.findStudentByName(lastName, firstName);

            if (loggingStudentLogin.isPresent() || loggingStudentLoginByName.isPresent() ) {
                StudentLogin studentLoginToLogout = loggingStudentLogin.orElseGet(loggingStudentLoginByName::get);
                studentLoginToLogout.setTimeOut(LocalDateTime.now());
                System.out.println(studentLoginToLogout);
                StudentLogin nextStudent = studentLoginQueue.getNextStudent();
                nextStudent.setTimeIn(LocalDateTime.now());
                studentLoginQueue.removeStudent(studentLoginToLogout);
                loggedOutStudent = studentLoginToLogout.getStudent();


                studentHistory = DataCenter.getInstance().getStudentHistory();
                studentHistory.addStudent(studentLoginToLogout);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Logout Successful");
                alert.setHeaderText("Student logged out");
                alert.setContentText("Student has been logged out of the system");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logout Error");
                alert.setHeaderText("Student not logged in");
                alert.setContentText("Please log in first");
                alert.showAndWait();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Successful");
            alert.setHeaderText("Student logged out");
            alert.setContentText("Student has been logged out of the system");
            alert.showAndWait();

        }

    }

    @FXML
    private void adminAndTutorLink(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/adminLog.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void createAccountLinkOnAction(ActionEvent event) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/AddStudent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void queueLinkOnAction(ActionEvent event) throws Exception {
       try {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/QueueStudentView.fxml"));
              Parent root = loader.load();
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(new Scene(root));
              stage.show();
       }
         catch (Exception e){
              e.printStackTrace();
         }
    }

    @FXML
    private void onKeyTyped(KeyEvent event) {
        if (!firstName.getText().isEmpty() || !lastName.getText().isEmpty()) {
            sId.setDisable(true);
        } else {
            sId.setDisable(false);
        }
    }

    private void configureTextField(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[a-zA-Z]")) {
                event.consume();
            }
            if (!firstName.getText().isEmpty() || !lastName.getText().isEmpty()) {
                sId.setDisable(true);
            } else {
                sId.setDisable(false);
            }
        });
    }

    private void initializeStudentIDField() {
        sId.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!isNumeric(character) || sId.getText().length() >= 8) {
                event.consume();
            }
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("[0-9]");
    }


}


