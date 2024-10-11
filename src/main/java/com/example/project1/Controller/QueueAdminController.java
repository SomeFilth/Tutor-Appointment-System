package com.example.project1.Controller;

import com.example.project1.Util.DataCenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class QueueAdminController implements Initializable {
    private FileChooser fileChooser = new FileChooser();

    private File file;
    @FXML
    private Button queue, history, export, importData, logout;
    @FXML
    private StackPane stackPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        DataCenter dataCenter = DataCenter.getInstance();

    }

    @FXML
    public void queueButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/Queue.fxml"));
        try {
            Parent root = loader.load();
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void historyButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/history.fxml"));
        try {
            Parent root = loader.load();
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportButtonOnAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project1/Views/export.fxml"));
        try {
            Parent root = loader.load();
            stackPane.getChildren().removeAll();
            stackPane.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void importDataButtonOnAction() {
        File initialDirectory = new File("D:\\CSE 218\\Project1\\src\\main\\Data\\");
        fileChooser.setInitialDirectory(initialDirectory);
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String filePath = file.getAbsolutePath();
                File file = new File(filePath);
                String fileName = file.getName();
                int pos = fileName.lastIndexOf(".");
                if (pos > 0) {
                    String baseName = fileName.substring(0, pos);
                    DataCenter dataCenter = DataCenter.getInstance();
                    dataCenter.importData(baseName);

                    dataCenter.loadStudents();
                    dataCenter.loadHistory();
                } else {
                    System.out.println("No file extension found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void logoutButtonOnAction(ActionEvent event) {
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
