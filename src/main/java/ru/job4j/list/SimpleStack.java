package ru.job4j.list;

public class SimpleStack<T> {

    private final ForwardLinked<T> linked = new ForwardLinked<>();

    /**
     * должен возвращать значение и удалять его из коллекции.
     * @return значение.
     */
    public T pop() {
        return linked.deleteFirst();
    }

    /**
     * помещает значение в коллекцию.
     * @param value значение.
     */
    public void push(T value) {
        linked.addFirst(value);
    }
}