package com.example.project1.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class csvExport {
    public static void csvExport(String filePath, String csv) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(csv);
            bw.newLine();
        }
    }
}
