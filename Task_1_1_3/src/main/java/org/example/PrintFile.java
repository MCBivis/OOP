package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class PrintFile implements IPrintable{
    String fileName;

    public PrintFile(String fileName) {
        this.fileName = fileName;
    }

    public void print(String expression) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(expression);
            writer.close();//!!!
        } catch (IOException e) {
            System.out.println("Writing in file error: " + e.getMessage());
        }
    }
}
