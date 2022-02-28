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
import java.util.List;
import java.util.function.Predicate;

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

        Path directoryPath = Paths.get(directory);
        List<Path> pathList = search(directoryPath, path -> path.toFile().getName().equals(name));
        if ("name".equals(type)) {
            pathList = search(directoryPath, path -> path.toFile().getName().equals(name));
        } else if ("mask".equals(type)) {
            pathList = search(directoryPath, path -> path.toFile().getName().endsWith(name));
        }
        writeDataInFile(output, pathList);
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
