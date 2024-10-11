package com.example.project1.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {


    @FXML
    private TextField adminUsername, tutorStudentID, tutorFirstName, tutorLastName;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private Button adminLoginButton, returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void adminLoginButtonOnAction() throws Exception{
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        if (validLogin(username, password)) {
            System.out.println("Login Successful");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/QueueTutorView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) adminLoginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Admin not found");
            alert.setContentText("Please enter a valid admin username and password");
            alert.showAndWait();
        }
    }

    @FXML
    public void returnOnActionButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/Main.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public boolean validLogin(String username, String password) {
        if (username.equals("Admin") && password.equals("SCCC")) {
            return true;
        } else {
            return false;
        }
    }
}
