package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
        if (size > capacity * LOAD_FACTOR) {
            expand();
        }
        if (table[index] != null) {
            isPut = false;
        } else {
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
        int hash;
        if (key == null) {
            hash = 0;
        } else {
            hash = key.hashCode();
            hash ^= hash >>> 16;
        }
        return hash;
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
        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry != null) {
                int newIndex = indexFor(hash(mapEntry.key));
                newTable[newIndex] = new MapEntry<>(mapEntry.key, mapEntry.value);
            }
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
        V value = null;
        int index = indexFor(hash(key));
        MapEntry<K, V> entry = table[index];
        if (entry != null && entry.key == key) {
            value = entry.value;
        }
        return value;
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
        MapEntry<K, V> entry = table[index];
        if (entry != null && entry.key == key) {
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