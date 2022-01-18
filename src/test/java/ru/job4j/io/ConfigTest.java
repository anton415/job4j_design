package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

public class ConfigTest {

    @Test
    public void whenPair() {
        String path = "app.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.username"),is("postgres"));
    }

    @Test
    public void whenDontGetCommentedValue() {
        String path = "app.properties";
        Config config = new Config(path);
        config.load();
        assertNull(config.value("hibernate.connection.password"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongFileProperties() {
        String path = "wrong-app.properties";
        Config config = new Config(path);
        config.load();
    }
}