package com.example.project1.Controller;

import com.example.project1.Model.Student;
import com.example.project1.Model.StudentLinkList;
import com.example.project1.Util.DataCenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddStudent implements Initializable{

    private StudentLinkList studentLinkList;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField studentID;

    @FXML
    private Button addStudent;

    @FXML
    private Button returnButton;

    @FXML
    private RadioButton CSE110, CSE118, CSE148, CSE218, CSE222, CSE248;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataCenter dataCenter = DataCenter.getInstance();
        studentLinkList = dataCenter.getStudentLinkList();
        initializeStudentIDField();
        configureTextField(firstName);
        configureTextField(lastName);
    }

    @FXML
    public void addStudentButtonOnAction(ActionEvent event) {
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || studentID.getText().isEmpty() || !isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        } else {

            String firstName = this.firstName.getText(), lastName = this.lastName.getText(), studentID = this.studentID.getText();
            Optional<Student> nameCheck = studentLinkList.findStudentByName(lastName, firstName);
            Optional<Student> idCheck = studentLinkList.findStudentById(studentID);
            if(nameCheck.isEmpty() && idCheck.isEmpty()){
                String fullName = fullName(lastName, firstName);
                String courses = getCourses();
                DataCenter dataCenter = DataCenter.getInstance();
                dataCenter.insertStudent(studentID, fullName, courses);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Student added");
                alert.setContentText("Student has been added to the system");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/Main.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Student already exists");
                alert.setContentText("Please enter a your name or your student ID");
                alert.showAndWait();
            }
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

    public String fullName(String lastName, String firstName){
        return lastName + " " + firstName;
    }

    private void configureTextField(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[a-zA-Z]")) {
                event.consume();
            }
        });
    }

    private void initializeStudentIDField() {
        studentID.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!isNumeric(character) || studentID.getText().length() >= 8) {
                event.consume();
            }
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("[0-9]");
    }

    private String getCourses() {
        StringBuilder courses = new StringBuilder();
        if (CSE110.isSelected()) {
            courses.append("CSE110 ");
        }
        if (CSE118.isSelected()) {
            courses.append("CSE118 ");
        }
        if (CSE148.isSelected()) {
            courses.append("CSE148 ");
        }
        if (CSE218.isSelected()) {
            courses.append("CSE218 ");
        }
        if (CSE222.isSelected()) {
            courses.append("CSE222 ");
        }
        if (CSE248.isSelected()) {
            courses.append("CSE248 ");
        }

        return courses.toString().trim();
    }

    private boolean isSelected() {
        return CSE110.isSelected() || CSE118.isSelected() || CSE148.isSelected() || CSE218.isSelected() || CSE222.isSelected() || CSE248.isSelected();
    }

}
