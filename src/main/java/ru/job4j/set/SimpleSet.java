package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

/**
 * Коллекция на массиве не должна хранить дубликаты.
 * @param <T>
 */
public class SimpleSet<T> implements Set<T> {

    private final SimpleArrayList<T> set = new SimpleArrayList<>();

    @Override
    public boolean add(T value) {
        boolean isAdded = !this.contains(value);
        if (isAdded) {
            set.add(value);
        }
        return isAdded;
    }

    @Override
    public boolean contains(T value) {
        boolean isContains = false;
        for (T element : set) {
            if (Objects.equals(element, value)) {
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