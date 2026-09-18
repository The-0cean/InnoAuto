package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LectureAssertTest {
    private static final Random RANDOM = new Random();

    @Test
    public void testIsEvenReturnsBoolean() {
        int n = RANDOM.nextInt(50) * 2;

        assertThat(Tasks.isEven(n))
                .as("isEven(%d) must return true for an even number", n)
                .isTrue();
    }

    @Test
    public void testRemoveSpecificNameReturnsList() {
        List<String> names = List.of("Alice", "Mary", "Igor", "Mary");

        List<String> actual = Tasks.removeSpecificName(names, "Mary");

        assertThat(actual)
                .as("removeSpecificName must return list without 'Mary'")
                .containsExactly("Alice", "Igor")
                .doesNotContain("Mary")
                .hasSize(2);
    }

    @Test
    public void testFindMaxThrowsOnEmptyArray() {
        int[] empty = {};

        assertThatThrownBy(() -> Tasks.findMax(empty))
                .as("findMax must throw on empty array")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Tag("failing")
    @Test
    public void testIsPositiveOnZero() {
        int zero = 0;

        assertThat(Tasks.isPositive(zero))
                .as("isPositive(0): zero is not positive, expecting false")
                .isFalse();
    }
}
