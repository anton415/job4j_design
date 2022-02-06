package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Задача класса прочитать данные из CSV файла и вывести их.
 */
public class CSVReader {
    private static ArgsName validation(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Number of parameters must be 4.");
        }
        ArgsName argsName = ArgsName.of(args);
        String path = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String filter = argsName.get("filter");
        if (!path.endsWith(".csv")) {
            throw new IllegalArgumentException("File must be csv.");
        }
        if (!Path.of(path).toFile().exists()) {
            throw new IllegalArgumentException("File does not exist.");
        }
        if (!";".equals(delimiter)) {
            throw new IllegalArgumentException("Delimiter must be \";\".");
        }
        if (out == null || out.isEmpty()) {
            throw new IllegalArgumentException("Out can't be empty.");
        }
        if (filter == null || filter.isEmpty()) {
            throw new IllegalArgumentException("Filter can't be empty.");
        }
        return argsName;
    }

    public static void handle(ArgsName argsName) {
        List<String> result = new LinkedList<>();
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        List<String> requiredColumnNames = Arrays.asList(argsName.get("filter").split(","));
        List<Integer> requiredColumnIndexes = new LinkedList<>();

        try (Scanner scanner = new Scanner(new File(argsName.get("path")))) {
            List<String> firstLine = Arrays.asList(scanner.nextLine().split(delimiter));
            requiredColumnNames.forEach(columnName -> requiredColumnIndexes.add(firstLine.indexOf(columnName)));
            requiredColumnIndexes.forEach(index -> result.add(firstLine.get(index)));

            while (scanner.hasNextLine()) {
                List<String> line = Arrays.asList(scanner.nextLine().split(delimiter));
                requiredColumnIndexes.forEach(index -> result.add(line.get(index)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if ("stdout".equals(out)) {
            StringBuilder lineForPrint = new StringBuilder();
            for (int i = 0; i < result.size();) {
                for (int j = 0; j < requiredColumnIndexes.size(); j++) {
                    lineForPrint.append(result.get(i++));
                    if (j == requiredColumnIndexes.size() - 1) {
                        System.out.println(lineForPrint);
                    } else {
                        lineForPrint.append(delimiter);
                    }
                }
                lineForPrint.setLength(0);
            }
        } else {
            try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(out)))) {
                StringBuilder lineForPrint = new StringBuilder();
                for (int i = 0; i < result.size();) {
                    for (int j = 0; j < requiredColumnIndexes.size(); j++) {
                        lineForPrint.append(result.get(i++));
                        if (j == requiredColumnIndexes.size() - 1) {
                            writer.println(lineForPrint);
                        } else {
                            lineForPrint.append(delimiter);
                        }
                    }
                    lineForPrint.setLength(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * В качестве входных данных задается путь к файлу path, разделитель delimiter, приемник данных out и фильтр по
     * столбцам filter.
     * @param args -path=data/file.csv -delimiter=";"  -out=stdout -filter=name,age
     * @throws Exception - чтение файла.
     */
    public static void main(String[] args) throws Exception {
        handle(validation(args));
    }
}