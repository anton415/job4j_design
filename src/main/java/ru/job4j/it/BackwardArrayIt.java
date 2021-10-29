package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для массива. Итератор должен отдавать элементы в обратном порядке.
 */
public class BackwardArrayIt implements Iterator<Integer> {
    private final int[] data;
    private int point;

    /**
     * Конструктор.
     * @param data - массив.
     */
    public BackwardArrayIt(int[] data) {
        this.data = data;
        this.point = data.length - 1;
    }

    @Override
    public boolean hasNext() {
        return point > -1;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[point--];
    }
}
