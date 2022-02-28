package ru.job4j.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import ru.job4j.io.ArgsName;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SearchFileTest {
    @Test
    public void whenValidationPass() {
        ArgsName args = SearchFile.validation(new String[] {"-d=data", "-n=file.csv", "-t=name", "-o=data/log.txt"});
        assertThat(args.get("d"), is("data"));
        assertThat(args.get("n"), is("file.csv"));
        assertThat(args.get("t"), is("name"));
        assertThat(args.get("o"), is("data/log.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValidationNotPass() {
        SearchFile.validation(new String[] {"-d=?", "-n=file.csv", "-t=name", "-o=data/log.txt"});
    }

    @Test
    public void whenSearchFileIsExist() throws IOException {
        String fileName = "file.csv";
        String pathName = "data";
        List<Path> pathList = SearchFile.search(Path.of(pathName), path -> path.toFile().getName().endsWith(fileName));
        assertThat(pathList.get(0).getFileName().toString(), is(fileName));
    }
}