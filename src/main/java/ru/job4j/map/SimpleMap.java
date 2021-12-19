package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Собственная карта.
 * @param <K> ключ.
 * @param <V> значение.
 */
public class SimpleMap<K, V> implements Map<K, V> {

    /**
     * Коэффициент увеличения карты.
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Размер карты.
     */
    private int capacity = 8;

    /**
     * Количество элементов в карте.
     */
    private int size = 0;

    /**
     * Количество изменений карты.
     */
    private int modCount = 0;

    /**
     * Карта с элементами.
     */
    @SuppressWarnings({"unchecked"})
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    /**
     * Добавление элемента.
     */
    @Override
    public boolean put(K key, V value) {
        boolean isPut;
        int index = indexFor(hash(key));
        if (table[index] != null) {
            isPut = false;
        } else {
            if (size > capacity * LOAD_FACTOR) {
                expand();
            }
            table[index] = new MapEntry<>(key, value);
            modCount++;
            size++;
            isPut = true;
        }
        return isPut;
    }

    /**
     * Получение хэш.
     * @param key ключ.
     * @return хэш.
     */
    private int hash(K key) {
        return Objects.hashCode(key);
    }

    /**
     * Получение индекса по хэш.
     * @param hash хэш.
     * @return индекс.
     */
    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    /**
     * Увеличение размера карты.
     */
    @SuppressWarnings({"unchecked"})
    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (int i = 0; i < size; i++) {
            MapEntry<K, V> mapEntry = table[i];
            newTable[indexFor(mapEntry.hashCode())] = mapEntry;
        }
        table = newTable;
    }

    /**
     * Получение элемента.
     * @param key ключ.
     * @return значение.
     */
    @Override
    public V get(K key) {
        int index = indexFor(hash(key));
        MapEntry<K, V> entry = table[index];
        return entry != null ? entry.value : null;
    }

    /**
     * Удаление элемента.
     * @param key ключ.
     * @return true если элемент удален успешно.
     */
    @Override
    public boolean remove(K key) {
        boolean isRemove = false;
        int index = indexFor(hash(key));
        if (table[index] != null) {
            table[index] = null;
            modCount++;
            size--;
            isRemove = true;
        }
        return isRemove;
    }

    /**
     * Итератор.
     * @return итератор.
     */
    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            final int iteratorModCount = modCount;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (iteratorModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < capacity && table[index] == null) {
                    index++;
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    /**
     * Основа карты.
     * @param <K> ключ.
     * @param <V> значение.
     */
    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }
}