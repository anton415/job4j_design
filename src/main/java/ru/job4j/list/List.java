package ru.job4j.list;

public interface List<T> extends Iterable<T> {
    /**
     * Добавление элемента.
     * @param value значение, которое добавляется в коллекцию.
     */
    void add(T value);

    /**
     * Получение элемента.
     * @param index индекс элемента.
     * @return элемент.
     */
    T get(int index);

}
