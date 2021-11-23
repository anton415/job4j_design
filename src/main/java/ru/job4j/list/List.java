package ru.job4j.list;

public interface List<T> extends Iterable<T> {
    /**
     * Добавление элемента.
     * @param value значение, которое добавляется в коллекцию.
     */
    void add(T value);

    /**
     * Изменение значения.
     * @param index индекс элемента.
     * @param newValue новое значение.
     * @return старое значение.
     */
    T set(int index, T newValue);

    /**
     * Удаление элемента.
     * @param index индекс элемента.
     * @return старое значение.
     */
    T remove(int index);

    /**
     * Получение элемента.
     * @param index индекс элемента.
     * @return элемент.
     */
    T get(int index);

    /**
     * Получение количества элементов в коллекции.
     * @return количество элементов в коллекции.
     */
    int size();
}
