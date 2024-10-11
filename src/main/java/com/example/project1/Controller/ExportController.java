package com.example.project1.Controller;

import com.example.project1.Model.StudentLogin;
import com.example.project1.Util.DataCenter;
import com.example.project1.Util.TxTWriter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class ExportController implements Initializable {

    private LocalDate startDate, endDate;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private TextArea exportTextArea;
    @FXML
    private Button exportButton, generateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exportTextArea.setEditable(false);
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
    }

    @FXML
    public void generateButtonOnAction() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        checkAndShowDateWarning(startDate, endDate);

        exportTextArea.clear();

        Duration totalDuration = Duration.ZERO;
        LocalDate previousDate = null;

        for (StudentLogin student : DataCenter.getInstance().getStudentHistory().getStudentHistory()) {
            LocalDate currentDate = student.getDate();

            if (currentDate.isEqual(startDate) || (currentDate.isAfter(startDate) && currentDate.isBefore(endDate))) {
                if (previousDate != null && !previousDate.isEqual(currentDate)) {
                    exportTextArea.appendText("--------------------------------------------------\n");
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
                String formattedTimeIn = student.getTimeIn() != null ? student.getTimeIn().format(formatter) : "N/A";
                String formattedTimeOut = student.getTimeOut() != null ? student.getTimeOut().format(formatter) : "N/A";

                exportTextArea.appendText("Student name: " + student.getStudentLogin().getName() + "\n");
                exportTextArea.appendText("Student id: " + student.getStudentLogin().getId() + "\n");
                exportTextArea.appendText("Course: " + student.getStudent().getCourse() + "\n");
                exportTextArea.appendText("Date: " + currentDate + "\n");
                exportTextArea.appendText("Time In: " + formattedTimeIn + "\n");
                exportTextArea.appendText("Time Out: " + formattedTimeOut + "\n");
                exportTextArea.appendText("Help: " + student.getCourseHelp() + "\n");
                String timeSpent = calculateTimeSpent(student);
                exportTextArea.appendText("Time Spent: " + timeSpent + "\n");
                exportTextArea.appendText("\n");

                String[] timeParts = timeSpent.split(" ");
                if (timeParts.length == 4) {
                    try {
                        long hours = Long.parseLong(timeParts[0]);
                        long minutes = Long.parseLong(timeParts[2]);
                        totalDuration = totalDuration.plusHours(hours).plusMinutes(minutes);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                previousDate = currentDate;
            }
        }
        exportTextArea.appendText("--------------------------------------------------\n");
        long totalHours = totalDuration.toHours();
        long totalMinutes = totalDuration.toMinutesPart();
        exportTextArea.appendText("Hours Tutoring: " + totalHours + " hours " + totalMinutes + " minutes\n");
    }


    @FXML
    public void exportButtonOnAction() throws IOException {

        String textToExport = exportTextArea.getText();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "report_" + timestamp + ".txt";
        TxTWriter.exportTXT("src/main/Export/" + fileName, textToExport);

        String csvText = convertTextToCSV(textToExport);
        String csvFileName = "report_" + timestamp + ".csv";
        TxTWriter.exportTXT("src/main/csvExporter/" + csvFileName, csvText);
    }

    private static String calculateTimeSpent(StudentLogin student) {
        LocalDateTime timeIn = student.getTimeIn();
        LocalDateTime timeOut = student.getTimeOut();

        if (timeIn != null && timeOut != null) {
            Duration duration = Duration.between(timeIn, timeOut);
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            return hours + " hours " + minutes + " minutes";
        }

        return "N/A";
    }

    private void checkAndShowDateWarning(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = LocalDate.now();

        if (!endDate.isEqual(currentDate)) {
            endDatePicker.setValue(currentDate);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("From date is not the current date");
            alert.setContentText("Please select the current date for accurate results.");
            alert.showAndWait();
        }
    }

    public String convertTextToCSV(String textToExport) {
        StringBuilder csvBuilder = new StringBuilder();
        String[] lines = textToExport.split("\n");

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length >= 2) {
                String fieldName = parts[0].trim();
                String fieldValue = parts[1].trim();
                csvBuilder.append(fieldName).append(",").append(fieldValue).append("\n");
            }
        }

        return csvBuilder.toString();
    }
}

