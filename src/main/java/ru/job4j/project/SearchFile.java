package ru.job4j.project;

import ru.job4j.io.ArgsName;
import ru.job4j.io.SearchFiles;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Программа для поиска файла.
 * Необходимо использовать Ключи при запуске программы:
 *     d - директория, в которой начинать поиск.
 *     n - имя файла, маска, либо регулярное выражение.
 *     t - тип поиска: mask искать по маске, name по полному совпадение имени.
 *     o - результат записать в файл.
 *
 * @author Антон Сердюченко.
 * @since 27.02.2022.
 */
public class SearchFile {
    public static void main(String[] args) throws IOException {
        ArgsName arguments = validation(args);
        String directory = arguments.get("d");
        String name = arguments.get("n");
        String type = arguments.get("t");
        String output = arguments.get("o");

        Predicate<Path> predicate = getPredicate(type, name);
        List<Path> pathList = search(Paths.get(directory), predicate);
        writeDataInFile(output, pathList);
    }

    private static Predicate<Path> getPredicate(String type, String name) {
        Predicate<Path> predicate = x -> false;
        if ("mask".equals(type)) {
            name = name.replace("*", ".*");
            name = name.replace("?", ".");
            Pattern pattern = Pattern.compile(name);
            predicate = path -> pattern.matcher(path.getFileName().toString()).find();
        } else if ("name".equals(type)) {
            String finalArgValue = name;
            predicate = path -> path.getFileName().toString().equals(finalArgValue);
        }
        return predicate;
    }

    public static void writeDataInFile(String output, List<Path> pathList) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(output, StandardCharsets.UTF_8))) {
            pathList.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    public static ArgsName validation(String[] args) {
        ArgsName arguments = ArgsName.of(args);
        if (args.length != 4) {
            throw new IllegalArgumentException("Incorrect number of arguments.");
        }
        if (Files.notExists(Paths.get(arguments.get("d")))) {
            throw new IllegalArgumentException(String.format("Directory %s does not exist!", arguments.get("d")));
        }
        if (!("name".equals(arguments.get("t")) || "mask".equals(arguments.get("t")))) {
            throw new IllegalArgumentException(String.format("Incorrect type of search: %s", arguments.get("d")));
        }
        return arguments;
    }
}
