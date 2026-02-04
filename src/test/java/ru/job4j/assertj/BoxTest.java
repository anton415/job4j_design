package ru.job4j.assertj;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube");
    }

    @Test
    void isThisUnknown() {
        Box box = new Box(5, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object");
    }

    @Test
    void whenNumberOfVertices4() {
        Box box = new Box(4, 10);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isEqualTo(4);
    }

    @Test
    void whenNumberOfVertices0() {
        Box box = new Box(0, 10);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isEqualTo(0);
    }

    @Test
    void whenNumberOfVertices8() {
        Box box = new Box(8, 10);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isEqualTo(8);
    }

    @Test
    void whenNumberOfVerticesNegative1() {
        Box box = new Box(5, 10);
        int vertices = box.getNumberOfVertices();
        assertThat(vertices).isEqualTo(-1);
    }

    @Test
    void whenBoxExists() {
        Box box = new Box(4, 10);
        boolean exists = box.isExist();
        assertThat(exists).isTrue();
    }

    @Test
    void whenBoxDoesNotExist() {
        Box box = new Box(5, 10);
        boolean exists = box.isExist();
        assertThat(exists).isFalse();
    }

    @Test
    void whenAreaSphere() {
        Box box = new Box(0, 10);
        double area = box.getArea();
        assertThat(area).isEqualTo(1256.63d, withPrecision(0.01d))
            .isCloseTo(1256.64d, withPrecision(0.01d))
            .isCloseTo(1256.64d, Percentage.withPercentage(1.0d))
            .isGreaterThan(1256.63d)
            .isLessThan(1256.64d);
    }

    @Test
    void whenAreaTetrahedron() {
        Box box = new Box(4, 10);
        double area = box.getArea();
        assertThat(area).isEqualTo(173.20d, withPrecision(0.01d))
            .isCloseTo(173.20d, withPrecision(0.01d))
            .isCloseTo(173.20d, Percentage.withPercentage(1.0d))
            .isGreaterThan(173.19d)
            .isLessThan(173.21d);
    }
}
