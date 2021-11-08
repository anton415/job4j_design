package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;

        if (data.length == 0) {
            return false;
        }

        for (int i = row; i < data.length; i++) {
            for (int j = column; j < data[i].length; j++) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        while (data[row].length == 0) {
            row++;
        }

        Integer integer = data[row][column];

        if (column < data[row].length - 1) {
            column++;
        } else {
            row++;
            column = 0;
        }

        return integer;
    }
}