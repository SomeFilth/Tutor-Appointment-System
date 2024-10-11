package com.example.project1.Util;
import com.example.project1.Model.Student;
import com.example.project1.Model.StudentLinkList;

import java.io.BufferedReader;
import java.io.FileReader;


public class CSVReader {

    public static StudentLinkList readCSV(String file) {

        StudentLinkList studentLinkList = new StudentLinkList();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();//skip the first line
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");
                String id = data[0].trim();
                String name = data[1].trim();
                String course = data[2].trim();
                Student student = new Student(id, name, course);
                studentLinkList.addStudent(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentLinkList;
    }
}