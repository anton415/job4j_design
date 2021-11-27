package ru.job4j.list;

public class SimpleStack<T> {

    private final ForwardLinked<T> linked = new ForwardLinked<>();

    int size;

    /**
     * должен возвращать значение и удалять его из коллекции.
     * @return значение.
     */
    public T pop() {
        size--;
        return linked.deleteFirst();
    }

    /**
     * помещает значение в коллекцию.
     * @param value значение.
     */
    public void push(T value) {
        linked.addFirst(value);
        size++;
    }

    /**
     * количество значений в коллекции.
     * @return количество значений.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this collection contains no elements.
     * @return true if this collection contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
}