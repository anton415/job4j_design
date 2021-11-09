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
        int j = column;
        int i = row;

        while (i < data.length) {
            if (j < data[i].length) {
                result = true;
                break;
            }
            j = 0;
            i++;
        }

        return result;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Integer integer = null;
        while (row < data.length) {
            if (data[row].length != 0 && column < data[row].length) {
                integer = data[row][column];
                column++;
                break;
            } else {
                column = 0;
                row++;
            }
        }

        return integer;
    }
}