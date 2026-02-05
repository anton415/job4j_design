package ru.job4j.assertj;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(list).hasSize(5)
                .contains("three")
                .contains("four", Index.atIndex(3))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("first", "second", "three", "four", "five");
        assertThat(set).hasSize(5)
                .contains("second")
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("zero", "six");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        var map = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(map).hasSize(5)
                .containsKeys("first", "second", "three", "four", "five")
                .containsValues(0, 1, 2, 3, 4)
                .containsEntry("first", 0)
                .containsEntry("five", 4)
                .doesNotContainKey("zero")
                .doesNotContainValue(5);
    }
}