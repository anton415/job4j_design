package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.Config;

import java.sql.*;
import java.util.StringJoiner;

public class StatementDemo {
    private static final Logger LOG = LoggerFactory.getLogger(StatementDemo.class.getName());

    private static Connection getConnection() throws SQLException {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();

        String url = config.value("postgres.url");
        String login = config.value("postgres.login");
        String password = config.value("postgres.password");
        return DriverManager.getConnection(url, login, password);
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format(
                        "create table if not exists demo_table(%s, %s);",
                        "id serial primary key",
                        "name text"
                );
                statement.execute(sql);
                LOG.info(getTableScheme(connection, "demo_table"));
            }
        }
    }

    public static String getTableScheme(Connection connection, String tableName) throws SQLException {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

}