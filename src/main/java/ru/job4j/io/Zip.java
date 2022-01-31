package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path path : sources) {
                zip.putNextEntry(new ZipEntry(path.toFile().getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(path.toFile()))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example of args: -d=data -e=.log -o=project.zip
    public static void main(String[] args) {
        ArgsName arguments = ArgsName.of(args);
        String directory = arguments.get("d");
        String extension = arguments.get("e");
        String output = arguments.get("o");

        if (Files.notExists(Paths.get(directory))) {
            throw new IllegalArgumentException(String.format("Directory %s does not exist!", directory));
        }

        try {
            List<Path> sources = Search.search(
                    Paths.get(directory),
                    path -> path.toFile().getName().endsWith(extension)
            );
            packFiles(sources, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}