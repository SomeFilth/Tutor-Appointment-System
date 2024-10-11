package com.example.project1.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TxTWriter {

    public static void exportTXT(String filePath, String txt) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(txt);
            bw.newLine();
        }
    }
}

