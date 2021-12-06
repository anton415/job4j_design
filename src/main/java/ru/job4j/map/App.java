package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String name = "Anton";
        int children = 0;
        Calendar birthday = Calendar.getInstance();
        User firstUser = new User(name, children, birthday);
        User secondUser = new User(name, children, birthday);
        Map<User, Object> users = new HashMap<>();
        users.put(firstUser, new Object());
        users.put(secondUser, new Object());
        users.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}