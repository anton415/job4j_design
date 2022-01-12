package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analise {

    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, User> map = new HashMap<>();
        current.forEach(user -> map.put(user.getId(), user));

        int added;
        int changed = 0;
        int deleted = 0;

        for (User user : previous) {
            if (map.containsKey(user.getId())) {
                if (!user.equals(map.get(user.getId()))) {
                    changed++;
                }
            } else {
                deleted++;
            }
        }

        added = current.size() - (previous.size() - deleted);
        return new Info(added, changed, deleted);
    }

}
