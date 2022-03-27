package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * Редактор таблицы.
 */
public class TableEditor implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class.getName());
    private Connection connection;
    private Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() {
        String url = this.properties.getProperty("postgres.url");
        String login = this.properties.getProperty("postgres.login");
        String password = this.properties.getProperty("postgres.password");

        try {
            this.connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            LOG.error("Exception in connection: ", e);
        }
    }

    private void executeStatement(String sql) {
        try {
            try (Statement statement = this.connection.createStatement()) {
                statement.execute(sql);
            }
        } catch (SQLException e) {
            LOG.error("Exception in execute statement: ", e);
        }
    }

    /**
     * Создает пустую таблицу без столбцов с указанным именем.
     * @param tableName - имя таблицы.
     */
    public void createTable(String tableName) throws SQLException {
        String sql = String.format(
                "create table if not exists %s(%s, %s);",
                tableName,
                "id serial primary key",
                "name text"
        );
        executeStatement(sql);
        LOG.info(getTableScheme(this.connection, tableName));
    }

    /**
     * Удаляет таблицу по указанному имени.
     * @param tableName - имя таблицы.
     */
    public void dropTable(String tableName) {
        String sql = String.format(
                "drop table %s;",
                tableName
        );
        executeStatement(sql);
    }

    /**
     * Добавляет столбец в таблицу.
     * ALTER TABLE table_name
     * ADD column_name datatype;
     *
     * @param tableName - имя таблицы.
     * @param columnName - имя колонки.
     * @param type - тип колонки.
     */
    public void addColumn(String tableName, String columnName, String type) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s ADD COLUMN IF NOT EXISTS %s %s;",
                tableName,
                columnName,
                type
        );
        executeStatement(sql);
        LOG.info(getTableScheme(this.connection, tableName));
    }

    /**
     * Удаляет столбец из таблицы.
     * ALTER TABLE Customers
     * DROP COLUMN ContactName;
     * @param tableName - имя таблицы.
     * @param columnName - имя колонки.
     */
    public void dropColumn(String tableName, String columnName) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s DROP %s;",
                tableName,
                columnName
        );
        executeStatement(sql);
        LOG.info(getTableScheme(this.connection, tableName));
    }

    /**
     * Переименовывает столбец.
     * ALTER TABLE Test1 RENAME COLUMN foo TO baz;
     * @param tableName - имя таблицы.
     * @param columnName - имя колонки.
     * @param newColumnName - новое имя колонки.
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s RENAME COLUMN %s TO %s;",
                tableName,
                columnName,
                newColumnName
        );
        executeStatement(sql);
        LOG.info(getTableScheme(this.connection, tableName));
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

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        String path = "data/app.properties";

        try (FileReader fileReader = new FileReader(path)) {
            properties.load(fileReader);
        } catch (IOException e) {
            LOG.error("Exception in properties load: ", e);
        }

        try (TableEditor tableEditor = new TableEditor(properties)) {
            String tableName = "demo_table";
            String columnName = "demo_column";
            String newColumnName = "new_demo_column";
            tableEditor.createTable(tableName);
            tableEditor.addColumn(tableName, columnName, "int");
            tableEditor.renameColumn(tableName, columnName, newColumnName);
            tableEditor.dropColumn(tableName, newColumnName);
            tableEditor.dropTable(tableName);
        } catch (Exception e) {
            LOG.error("Exception in connection: ", e);
        }
    }
}
