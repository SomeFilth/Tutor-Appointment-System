package com.example.project1.Application;
import com.example.project1.Model.StudentLinkList;
import com.example.project1.Util.CSVReader;
import com.example.project1.Util.DataCenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/project1/Views/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tutor Appointment System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}