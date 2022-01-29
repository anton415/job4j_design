package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments. You should to add argument.");
        }
        for (String arg : args) {
            String key = arg.substring(arg.indexOf("-") + 1, arg.indexOf("="));
            String value = arg.substring(arg.indexOf('=') + 1);
            if (key.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Some arguments are wrong. You should use pattern: -Xmx=512");
            }
            values.put(key, value);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}