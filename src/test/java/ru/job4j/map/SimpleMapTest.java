package ru.job4j.map;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenPutIsTrue() {
        Map<Integer, String> map = new SimpleMap<>();
        boolean isPut = map.put(1, "First");
        assertTrue(isPut);
    }

    @Test
    public void whenPutSecondTimeSameValueIsFalse() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "First");
        boolean isPut = map.put(1, "First");
        assertFalse(isPut);
    }

    @Test
    public void whenAddAndGet() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "First");
        map.put(2, "Second");
        map.put(3, "Third");
        assertThat(map.get(1), Is.is("First"));
        assertThat(map.get(2), Is.is("Second"));
        assertThat(map.get(3), Is.is("Third"));
    }

    @Test
    public void whenAddThenRemoveAndGet() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "First");
        boolean isRemove = map.remove(1);
        assertTrue(isRemove);
        assertNull(map.get(1));
    }

    @Test
    public void whenRemoveFalse() {
        Map<Integer, String> map = new SimpleMap<>();
        boolean isRemove = map.remove(1);
        assertFalse(isRemove);
    }

    @Test
    public void whenAddIterHasNextTrue() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "First");
        Iterator<Integer> it = map.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), Is.is(1));
    }

    @Test
    public void whenAddIterNextOne() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "First");
        Iterator<Integer> it = map.iterator();
        assertThat(it.next(), Is.is(1));
    }

    @Test
    public void whenHasNextIsFalse() {
        Map<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> it = map.iterator();
        assertFalse(it.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddAfterGetIteratorThenMustBeException() {
        Map<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> iterator = map.iterator();
        map.put(1, "First");
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAfterGetIteratorThenMustBeException() {
        Map<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> iterator = map.iterator();
        map.put(1, "First");
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIteratorFromEmptyListThenNextThrowException() {
        Map<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> iterator = map.iterator();
        iterator.next();
    }
}