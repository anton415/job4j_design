package ru.job4j.map;

import java.util.Calendar;

/**
 * Пользователь.
 */
public class User {
    /**
     * Имя.
     */
    private String name;

    /**
     * Количество детей.
     */
    private int children;

    /**
     * День рождения.
     */
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }
}
