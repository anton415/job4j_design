package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final List<FileProperty> duplicates = new LinkedList<>();
    private final Map<String, FileProperty> allFiles = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(Files.size(file), file.toFile().getName());
        String fileName = fileProperty.getName();
        boolean isDuplicate = allFiles.containsKey(fileName);
        boolean isInDuplicateList = duplicates.contains(allFiles.get(fileName));
        if (isDuplicate && !isInDuplicateList) {
            duplicates.add(fileProperty);
            duplicates.add(allFiles.get(fileName));
        } else if (isDuplicate) {
            duplicates.add(fileProperty);
        } else {
            allFiles.put(fileProperty.getName(), fileProperty);
        }

        return super.visitFile(file, attrs);
    }

    public Collection<FileProperty> getDuplicates() {
        return duplicates;
    }
}