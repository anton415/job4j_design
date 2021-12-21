package ru.job4j.tree;

import java.util.*;

/**
 * Элементарная структура дерева.
 * Каждый элемент дерева можем быть сам деревом. В дереве не могут храниться дубликаты.
 * @param <E> тип элементов в дереве.
 */
public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    /**
     * Должен находить узел по значению parent и добавлять в него дочерний узел со значением child.
     * @param parent - родитель, которому добавляется значение.
     * @param child - значение, которое добавляется.
     * @return если child есть, то метод должен вернуть false.
     */
    @Override
    public boolean add(E parent, E child) {
        boolean rsl = false;
        Optional<Node<E>> parentNode = findBy(parent);
        Optional<Node<E>> childNode = findBy(child);
        if (parentNode.isPresent() && childNode.isEmpty()) {
            parentNode.get().children.add(new Node<>(child));
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.value.equals(value)) {
                rsl = Optional.of(el);
                break;
            }
            data.addAll(el.children);
        }
        return rsl;
    }
}