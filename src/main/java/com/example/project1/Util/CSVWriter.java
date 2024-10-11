package com.example.project1.Util;

import com.example.project1.Model.Student;
import com.example.project1.Model.StudentLinkList;

import java.io.FileWriter;

public class CSVWriter {

    public static void writeCSV(String file, Student student) {
        try {
            FileWriter csvWriter = new FileWriter(file, true);
            csvWriter.append(student.getId());
            csvWriter.append(",");
            csvWriter.append(student.getName());
            csvWriter.append(",");
            csvWriter.append(student.getCourse());
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
