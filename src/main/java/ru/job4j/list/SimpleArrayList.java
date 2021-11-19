package ru.job4j.list;

import java.util.*;

/**
 * Динамический список на массиве, аналог ArrayList.
 * @param <T> любой тип для элемента.
 */
public class SimpleArrayList<T> implements List<T> {

    /**
     * Массив элементов.
     */
    private T[] container;

    /**
     * Количество элементов.
     */
    private int size;

    /**
     * Количество изменений. Значение увеличивается при каждом добавлении, удалении или изменении.
     */
    private int modCount;

    /**
     * Коллекция на основе массива.
     * @param capacity размер коллекции.
     */
    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    /**
     * Добавление элемента.
     * @param value значение, которое добавляется в коллекцию.
     */
    @Override
    public void add(T value) {
        if (size == container.length) {
            container = Arrays.copyOf(container, size * 2);
        }
        container[size] = value;
        size++;
        modCount++;
    }

    /**
     * Изменение значения.
     * @param index индекс элемента.
     * @param newValue новое значение.
     * @return старое значение.
     */
    @Override
    public T set(int index, T newValue) {
        T oldValue = container[index];
        container[index] = newValue;
        modCount++;
        return oldValue;
    }

    /**
     * Удаление элемента.
     * @param index индекс элемента.
     * @return старое значение.
     */
    @Override
    public T remove(int index) {
        T oldValue = container[index];
        System.arraycopy(container, index + 1, container, index, size - index);
        size--;
        modCount++;
        return oldValue;
    }

    /**
     * Получение элемента.
     * @param index индекс элемента.
     * @return элемент.
     */
    @Override
    public T get(int index) {
        return container[index];
    }

    /**
     * Получение количества элементов в коллекции.
     * @return количество элементов в коллекции.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Итератор.
     * @return итератор.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            /**
             * Количество изменений.
             * Если с момента создания итератора в коллекцию изменили,
             * итератор кидает ConcurrentModificationException.
             */
            final int expectedModCount = modCount;
            /**
             * Индекс итератора.
             */
            int iteratorIndex;

            /**
             * Есть ли следующий элемент.
             * @return true, если следующий элемент существует.
             */
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return iteratorIndex < size;
            }

            /**
             * Получение следующего элемента.
             * @return следующий элемент.
             */
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[iteratorIndex++];
            }
        };
    }
}