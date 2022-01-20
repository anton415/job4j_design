package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

public class ConfigTest {

    @Test
    public void whenPair() {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.username"), is("postgres"));
    }

    @Test
    public void whenDontGetCommentedValue() {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();
        assertNull(config.value("hibernate.connection.password"));
    }

    @Test(expected = PatternExceptionNoKey.class)
    public void whenWrongPatternNokey() {
        String path = "data/wrong-app-no-key.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = PatternExceptionNoValue.class)
    public void whenWrongPatternNoValue() {
        String path = "data/wrong-app-no-value.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = PatternExceptionNoEquals.class)
    public void whenWrongPatternNoEquals() {
        String path = "data/wrong-app-no-equals.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = PatternExceptionMultipleEquals.class)
    public void whenWrongPatternMultipleEquals() {
        String path = "data/wrong-app-multiple-equals.properties";
        Config config = new Config(path);
        config.load();
    }
}