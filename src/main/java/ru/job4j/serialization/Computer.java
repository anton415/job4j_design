package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.UsageLog4j;
import ru.job4j.question.User;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class Computer {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());
    private static final long serialVersionUID = 1L;
    // поля булево, какой-нибудь числовой тип, строковый тип, вложенный объект и массив.
    private boolean isTurnOn = true;
    private int age = 5;
    private String color = "Silver";
    private User user = new User(0, "Anton");
    private String[] programs = new String[] {"Chrome", "IDEA", "Terminal"};

    public Computer(boolean isTurnOn, int age, String color, User user, String[] programs) {
        this.isTurnOn = isTurnOn;
        this.age = age;
        this.color = color;
        this.user = user;
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Computer{" + "isTurnOn=" + isTurnOn + ", age=" + age + ", name='" + color + '\'' + ", user=" + user
                + ", programs=" + Arrays.toString(programs) + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Computer computer = new Computer(true, 5, "Silver", new User(0, "Anton"), new String[] {"Chrome", "IDEA"});
        /* Преобразуем объект computer в json-строку. */
        final Gson gson = new GsonBuilder().create();
        LOG.debug(gson.toJson(computer));

        /* Модифицируем json-строку */
        final String computerJson =
                "{"
                        + "\"isTurnOn\":true,"
                        + "\"age\":5,"
                        + "\"color\":\"Silver\","
                        + "\"user\":"
                        + "{"
                        + "\"id\":0,"
                        + "\"name\":\"Anton\""
                        + "},"
                        + "\"programs\":"
                        + "[\"Chrome\",\"IDEA\"]"
                        + "}";
        final Computer computerMod = gson.fromJson(computerJson, Computer.class);
        LOG.debug(computerMod.toString());
    }
}
