package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

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
        Optional<Node<E>> parentNode = findBy(parent);
        Optional<Node<E>> childNode = findBy(child);
        boolean isAdd = parentNode.isPresent() && childNode.isEmpty();
        if (isAdd) {
            parentNode.get().children.add(new Node<>(child));
        }
        return isAdd;
    }

    /**
     * Метод должен проверять количество дочерних элементов в дереве.
     * @return если количество дочерних элементов > 2 - то дерево не бинарное, возвращается false.
     */
    public boolean isBinary() {
        return findByPredicate(node -> node.children.size() > 2).isEmpty();
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(node -> node.value.equals(value));
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> node = data.poll();
            if (condition.test(node)) {
                result = Optional.of(node);
                break;
            }
            data.addAll(node.children);
        }
        return result;
    }
}