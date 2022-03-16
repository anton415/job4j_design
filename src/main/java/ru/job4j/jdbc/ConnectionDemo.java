package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionDemo.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();

        Class.forName(config.value("postgres.driver"));
        String url = config.value("postgres.url");
        String login = config.value("postgres.login");
        String password = config.value("postgres.password");

        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            LOG.info(metaData.getUserName());
            LOG.info(metaData.getURL());
        }
    }
}