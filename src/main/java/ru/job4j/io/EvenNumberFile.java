package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                if (read % 2 == 0) {
                    text.append((char) read);
                }
            }
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}