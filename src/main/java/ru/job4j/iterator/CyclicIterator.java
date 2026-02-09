package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {

    private final List<T> data;
    private int index;

    public CyclicIterator(List<T> data) {
        this.data = data;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return !data.isEmpty();
    }

    @Override
    public T next() {        
        if (data.isEmpty()) {
            throw new NoSuchElementException("Collection is empty");
        }
        T result = data.get(index);
        if (index < data.size() - 1) {
            index++;
        } else if (index == data.size() - 1) {
            index = 0;
        }
        return result;
    }
}