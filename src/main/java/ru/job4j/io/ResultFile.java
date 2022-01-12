package ru.job4j.io;

import java.io.FileOutputStream;

public class ResultFile {
    public static void main(String[] args) {
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            int size = 10;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int number = (i + 1) * (j + 1);
                    out.write(String.valueOf(number).getBytes());
                    out.write("\t".getBytes());
                }
                out.write(System.lineSeparator().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}