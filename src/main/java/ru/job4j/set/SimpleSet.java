package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;


public class SimpleSet<T> implements Set<T> {

    private final SimpleArrayList<T> set = new SimpleArrayList<>();

    @Override
    public boolean add(T value) {
        boolean isAdded = false;
        if (!this.contains(value)) {
            set.add(value);
            isAdded = true;
        }
        return isAdded;
    }

    @Override
    public boolean contains(T value) {
        boolean isContains = false;
        for (T t : this) {
            if (Objects.equals(t, value)) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}