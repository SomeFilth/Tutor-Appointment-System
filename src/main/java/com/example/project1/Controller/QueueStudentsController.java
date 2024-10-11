package com.example.project1.Controller;

import com.example.project1.Model.StudentLogin;
import com.example.project1.Model.StudentLoginQueue;
import com.example.project1.Util.DataCenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class QueueStudentsController implements Initializable {

    @FXML
    public TableView<StudentLogin> studentTable;
    @FXML
    private TableColumn<StudentLogin, String> nameColumn, courseColumn, courseHelpColumn;
    @FXML
    private Button returnButton;

    ObservableList<StudentLogin> studentQueueList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        DataCenter dataCenter = DataCenter.getInstance();
        queueList();
    }

    private void queueList() {
        studentTable.getItems().clear();

        StudentLoginQueue studentLoginQueue = DataCenter.getInstance().getStudentLoginQueue();
        LinkedList<StudentLogin> studentLogins = studentLoginQueue.getStudentLogins();

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getName()));
        courseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getCourse()));
        courseHelpColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseHelp()));

        studentLoginQueue.displayQueue();
        studentTable.getItems().addAll(studentLogins);
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



}
