package com.example.project1.Controller;

import com.example.project1.Model.StudentHistory;
import com.example.project1.Model.StudentLogin;
import com.example.project1.Util.DataCenter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private StudentHistory currentStudentHistory;

    @FXML
    private ListView<StudentLogin> historyList;
    @FXML
    private TextField name, date, id, course, timeIn, timeOut, timeSpent;
    @FXML
    private TextArea helpInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataCenter dataCenter = DataCenter.getInstance();
        dataCenter.loadHistory();
        currentStudentHistory = dataCenter.getStudentHistory();
        name.setEditable(false);
        date.setEditable(false);
        id.setEditable(false);
        course.setEditable(false);
        timeIn.setEditable(false);
        timeOut.setEditable(false);
        timeSpent.setEditable(false);
        helpInfo.setEditable(false);
        setupListView();
    }

    private void setupListView() {
        historyList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(StudentLogin item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getStudentLogin().getName() + " - " + item.getDate());
            }
        });

        historyList.setItems(FXCollections.observableArrayList(currentStudentHistory.getStudentHistory()));

        historyList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedStudent) -> {
            if (selectedStudent != null) {
                displayStudentInfo(selectedStudent);
            }
        });
    }

    private void displayStudentInfo(StudentLogin student) {
        name.setText(student.getStudentLogin().getName());
        date.setText(student.getDate().toString());
        id.setText(student.getStudentLogin().getId());
        course.setText(student.getStudent().getCourse());

        LocalDateTime in = student.getTimeIn();
        LocalDateTime out = student.getTimeOut();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");

        timeIn.setText(in != null ? in.format(formatter) : "N/A");
        timeOut.setText(out != null ? out.format(formatter) : "N/A");

        timeSpent.setText(calculateTimeSpent(student));
        helpInfo.setText(student.getHelpInfo());

        timeSpent.setText(calculateTimeSpent(student));
        helpInfo.setText(student.getHelpInfo());

        timeSpent.setText(calculateTimeSpent(student));
        helpInfo.setText(student.getHelpInfo());
    }

    private String calculateTimeSpent(StudentLogin student) {
        LocalDateTime timeIn = student.getTimeIn();
        LocalDateTime timeOut = student.getTimeOut();

        if (timeIn != null && timeOut != null) {
            long minutes = java.time.Duration.between(timeIn, timeOut).toMinutes();
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;

            return hours + " hours " + remainingMinutes + " minutes";
        }

        return "N/A";
    }
}
