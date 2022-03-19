package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

/**
 * Редактор таблицы.
 */
public class TableEditor implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TableEditor.class.getName());

    private Connection connection;

    public TableEditor() {
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
    public void createTable(String tableName) throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
          String sql = String.format(
            "create table if not exists %s(%s, %s);",
            tableName,
            "id serial primary key",
            "name text"
          );
          statement.execute(sql);
          LOG.info(getTableScheme(this.connection, tableName));
        }

    }

    /**
     * Удаляет таблицу по указанному имени.
     * @param tableName - имя таблицы.
     */
    public void dropTable(String tableName) throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
          String sql = String.format(
            "drop table %s;",
            tableName
          );
          statement.execute(sql);
        }
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
        try (Statement statement = this.connection.createStatement()) {
          String sql = String.format(
            "ALTER TABLE %s ADD COLUMN IF NOT EXISTS %s %s;",
            tableName,
            columnName,
            type
          );
          statement.execute(sql);
          LOG.info(getTableScheme(this.connection, tableName));
        }
    }

    /**
     * Удаляет столбец из таблицы.
     * ALTER TABLE Customers
     * DROP COLUMN ContactName;
     * @param tableName - имя таблицы.
     * @param columnName - имя колонки.
     */
    public void dropColumn(String tableName, String columnName) throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
          String sql = String.format(
            "ALTER TABLE %s DROP %s;",
            tableName,
            columnName
          );
          statement.execute(sql);
          LOG.info(getTableScheme(this.connection, tableName));
        }
    }

    /**
     * Переименовывает столбец.
     * ALTER TABLE Test1 RENAME COLUMN foo TO baz;
     * @param tableName - имя таблицы.
     * @param columnName - имя колонки.
     * @param newColumnName - новое имя колонки.
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
          String sql = String.format(
            "ALTER TABLE %s RENAME COLUMN %s TO %s;",
            tableName,
            columnName,
            newColumnName
          );
          statement.execute(sql);
          LOG.info(getTableScheme(this.connection, tableName));
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

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        try (TableEditor tableEditor = new TableEditor()) {
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
