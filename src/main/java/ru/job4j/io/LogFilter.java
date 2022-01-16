package ru.job4j.io;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogFilter {
    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> filter(String file) {
        List<String> stringList = null;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            stringList = in.lines()
                    .filter(s -> Objects.equals(s.split(" ")[s.split(" ").length - 2], "404"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        save(log, "404.txt");
    }
}