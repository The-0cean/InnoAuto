package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TasksCheckTest {
    private static final Random RANDOM = new Random();

    @RepeatedTest(10)
    public void testIsEven() {
        int n = RANDOM.nextInt(100) + 1;
        boolean expected = (n % 2 == 0);

        assertThat(Tasks.isEven(n))
                .as("isEven(%d)", n)
                .isEqualTo(expected);
    }

    @RepeatedTest(10)
    public void testIsPositive() {
        int n = RANDOM.nextInt(201) - 100;
        boolean expected = (n >= 0);

        assertThat(Tasks.isPositive(n))
                .as("isPositive(%d)", n)
                .isEqualTo(expected);
    }

    @RepeatedTest(10)
    public void testSumToN() {
        int n = RANDOM.nextInt(100) + 1;
        int expected = n * (n + 1) / 2;

        assertThat(Tasks.sumToN(n))
                .as("sumToN(%d)", n)
                .isEqualTo(expected);
    }

    @RepeatedTest(10)
    public void testCalcAverage() {
        int value = RANDOM.nextInt(100) + 1;
        int size = RANDOM.nextInt(5) + 1;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(value);
        }

        assertThat(Tasks.calcAverage(numbers))
                .as("calcAverage(%s)", numbers)
                .isEqualTo((double) value);
    }

    @RepeatedTest(10)
    public void testFindMax() {
        int size = RANDOM.nextInt(5) + 1;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(201) - 100;
        }
        int expected = Arrays.stream(arr).max().getAsInt();

        assertThat(Tasks.findMax(arr))
                .as("findMax(%s)", Arrays.toString(arr))
                .isEqualTo(expected);
    }

    @RepeatedTest(10)
    public void testHasBug() {
        String[] messages = {"All good", "Moving on", "Almost done"};
        boolean shouldHaveBug = RANDOM.nextBoolean();
        if (shouldHaveBug) {
            messages[RANDOM.nextInt(messages.length)] = "BUG";
        }

        assertThat(Tasks.hasBug(messages))
                .as("hasBug(%s)", Arrays.toString(messages))
                .isEqualTo(shouldHaveBug);
    }

    @RepeatedTest(10)
    public void testReverse() {
        int size = RANDOM.nextInt(4) + 2;
        String[] source = new String[size];
        for (int i = 0; i < size; i++) {
            source[i] = "item" + RANDOM.nextInt(100);
        }

        List<String> expected = new ArrayList<>(Arrays.asList(source));
        Collections.reverse(expected);

        assertThat(Tasks.reverse(source))
                .as("reverse(%s)", Arrays.toString(source))
                .containsExactlyElementsOf(expected);
    }

    @RepeatedTest(10)
    public void testRemoveSpecificName() {
        List<String> pool = List.of("Alice", "Mary", "Igor", "Poly", "Eva");
        String toRemove = pool.get(RANDOM.nextInt(pool.size()));

        List<String> expected = pool.stream()
                .filter(name -> !name.equals(toRemove))
                .toList();

        assertThat(Tasks.removeSpecificName(pool, toRemove))
                .as("removeSpecificName(%s, '%s')", pool, toRemove)
                .containsExactlyElementsOf(expected)
                .doesNotContain(toRemove);
    }

    // ======== ГРУППА 2: данные из таблицы в коде ========

    @ParameterizedTest
    @CsvSource({
            "0, Denied",
            "1, Denied",
            "17, Denied",
            "18, Denied",
            "19, Allowed",
            "20, Allowed",
            "50, Allowed",
            "99, Allowed",
            "100, Allowed",
            "-5, Denied"
    })
    public void testCheckAccess(int age, String expected) {
        assertThat(Tasks.checkAccess(age))
                .as("checkAccess(%d)", age)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, '1 Поехали!'",
            "2, '2 1 Поехали!'",
            "3, '3 2 1 Поехали!'",
            "4, '4 3 2 1 Поехали!'",
            "5, '5 4 3 2 1 Поехали!'",
            "6, '6 5 4 3 2 1 Поехали!'",
            "7, '7 6 5 4 3 2 1 Поехали!'",
            "8, '8 7 6 5 4 3 2 1 Поехали!'",
            "9, '9 8 7 6 5 4 3 2 1 Поехали!'",
            "10, '10 9 8 7 6 5 4 3 2 1 Поехали!'"
    })
    public void testBlastOff(int start, String expected) {
        assertThat(Tasks.blastOff(start))
                .as("blastOff(%d)", start)
                .isEqualTo(expected);
    }

    // ======== ГРУППА 3: данные из CSV-файлов ========

    @ParameterizedTest
    @CsvFileSource(resources = "/grades.csv", numLinesToSkip = 1)
    public void testGetGrade(int score, String expected) {
        assertThat(Tasks.getGrade(score))
                .as("getGrade(%d)", score)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/even-range.csv", numLinesToSkip = 1)
    public void testGetEvenInRange(int start, int end, String expected) {
        assertThat(Tasks.getEvenInRange(start, end))
                .as("getEvenInRange(%d, %d)", start, end)
                .isEqualTo(expected);
    }

}