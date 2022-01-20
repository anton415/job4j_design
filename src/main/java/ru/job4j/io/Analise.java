package ru.job4j.io;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Analise {
    private final List<String> periods = new LinkedList<>();
    private boolean isServerActive;

    public void unavailable(String source, String target) {

        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            StringBuilder period = new StringBuilder();
            in.lines().forEach(line -> {
                if (!isServerActive && (line.contains("400") || line.contains("500"))) {
                    isServerActive = true;
                    period.append(line.split("\\s")[1]);
                    period.append(";");
                } else if (isServerActive && (line.contains("200") || line.contains("300"))) {
                    isServerActive = false;
                    period.append(line.split("\\s")[1]);
                    periods.add(period.toString());
                    period.setLength(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            periods.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analise analise = new Analise();
        analise.unavailable("data/server.log", "data/analise.log");
    }
}