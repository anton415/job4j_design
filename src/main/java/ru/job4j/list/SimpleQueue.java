package ru.job4j.list;

/**
 * Очередь на двух стеках.
 * @param <T>
 */
public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    /**
     * Должен возвращать первое значение и удалять его из коллекции.
     * Если стек out пустой, перекладываем все элементы из in в out последовательными вызовами pop и push.
     * Если out не пуст, достаем элементы из него. Как только out окажется снова пустым повторяем ту же операцию.
     * @return значение.
     */
    public T poll() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
    }

    /**
     * Помещает значение в конец.
     * Операцию push будем всегда делать в стек in.
     * @param value значение.
     */
    public void push(T value) {
        in.push(value);
    }
}