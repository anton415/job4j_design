package ru.job4j.set;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleSetTest {

    @Test
    public void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertTrue(set.add(1));
        assertTrue(set.contains(1));
        assertFalse(set.add(1));
    }

    @Test
    public void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertTrue(set.add(null));
        assertTrue(set.contains(null));
        assertFalse(set.add(null));
    }

    @Test
    public void whenContainTrue() {
        Set<String> set = new SimpleSet<>();
        set.add("1");
        assertTrue(set.contains("1"));
    }

    @Test
    public void whenContainFalse() {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        assertFalse(set.contains(2));
    }
}