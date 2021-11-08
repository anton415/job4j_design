package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        if (data.length == 0) {
            return false;
        }

        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Integer integer = null;

        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                index = i + 1;
                integer = data[i];
                break;
            }
        }

        return integer;
    }

}