package ru.job4j.spammer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {
    private static final Logger LOG = LoggerFactory.getLogger(ImportDB.class.getName());

    private final Properties properties;
    private final String file;

    public ImportDB(Properties properties, String file) {
        this.properties = properties;
        this.file = file;
    }

    public List<User> load() {
        List<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int index = 0;
            bufferedReader.lines().forEach(line -> {
                String[] userAsArray = line.split(";", 3);
                if (userAsArray.length == 3 && !userAsArray[0].isEmpty() && !userAsArray[1].isEmpty()) {
                    users.add(index, new User(userAsArray[0], userAsArray[1]));
                } else {
                    throw new IllegalArgumentException("Убедитесь, что в массиве точно 2 элемента и они не пустые");
                }
            });
        } catch (Exception e) {
            LOG.error("Exception in file reader: ", e);
        }
        return users;
    }

    public void save(List<User> users) throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("jdbc.driver"));
        try (Connection connection = DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password")
        )) {
            for (User user : users) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("insert into dump(name, email) values (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, user.name);
                    preparedStatement.setString(2, user.email);
                    preparedStatement.execute();
                } catch (Exception e) {
                    LOG.error("Exception in prepared statement (insert): ", e);
                }
            }
        }
    }

    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("data/app.properties")) {
            properties.load(in);
        } catch (IOException e) {
            LOG.error("Exception in properties load: ", e);
        }
        ImportDB db = new ImportDB(properties, "data/dump.txt");
        db.save(db.load());
    }
}