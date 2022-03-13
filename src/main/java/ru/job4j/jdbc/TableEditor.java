package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.Config;

import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class.getName());

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();

        String url = config.value("postgres.url");
        String login = config.value("postgres.login");
        String password = config.value("postgres.password");

        try {
            this.connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            LOG.error("Exception in connection: ", e);
        }
    }

    /**
     * Создает пустую таблицу без столбцов с указанным именем.
     * @param tableName - имя таблицы.
     */
    public void createTable(String tableName) {
    }

    /**
     * Удаляет таблицу по указанному имени.
     * @param tableName
     */
    public void dropTable(String tableName) {
    }

    /**
     * Добавляет столбец в таблицу.
     * @param tableName
     * @param columnName
     * @param type
     */
    public void addColumn(String tableName, String columnName, String type) {
    }

    /**
     * Удаляет столбец из таблицы.
     * @param tableName
     * @param columnName
     */
    public void dropColumn(String tableName, String columnName) {
    }

    /**
     * Переименовывает столбец.
     * @param tableName
     * @param columnName
     * @param newColumnName
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) {
    }


    public static String getTableScheme(Connection connection, String tableName) throws Exception {
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

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}