package ru.job4j.serialization;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.question.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Computer.class.getName());

    public static void main(String[] args) {
        JSONObject jsonUser = new JSONObject("{\"id\":0,\"name\":\"Anton\"}");

        List<String> list = new ArrayList<>();
        list.add("Chrome");
        list.add("IDEA");
        JSONArray jsonPrograms = new JSONArray(list);

        final Computer computer = new Computer(true, 5, "Silver", new User(0, "Anton"), new String[] {"Chrome", "IDEA"});
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("turnOn", computer.isTurnOn());
        jsonObject.put("age", computer.getAge());
        jsonObject.put("color", computer.getColor());
        jsonObject.put("user", jsonUser);
        jsonObject.put("programs", jsonPrograms);

        LOG.debug(jsonObject.toString());
        LOG.debug(new JSONObject(computer).toString());
    }
}