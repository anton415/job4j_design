package ru.job4j.list;

import java.util.*;

/**
 * Контейнер на базе связанного списка.
 * @see LinkedList
 * @param <E>
 */
public class SimpleLinkedList<E> implements List<E> {
    /**
     * Количество элементов.
     */
    private int size;

    /**
     * Первый.
     */
    private Node<E> head;

    /**
     * Текущий.
     */
    private Node<E> current;

    /**
     * Количество изменений. Значение увеличивается при каждом добавлении, удалении или изменении.
     */
    private int modCount;

    @Override
    public void add(E value) {
        if (head == null) {
            head = new Node<>(value);
            current = head;
        } else {
            Node<E> temp = current;
            current.setNext(new Node<>(value));
            current = current.getNext();
            current.setPrev(temp);
        }
        size++;
        modCount++;
    }

    @Override
    public E set(int index, E newValue) {
        Objects.checkIndex(index, size);
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        node.setItem(newValue);
        modCount++;
        return node.getItem();
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        Node<E> prev = node.getPrev();
        Node<E> next = node.getNext();
        prev.setNext(node.getNext());
        next.setPrev(node.getNext());
        size--;
        modCount++;
        return node.getItem();
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getItem();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            final int expectedModCount = modCount;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(index++);
            }
        };
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E element) {
            this.item = element;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }
}
