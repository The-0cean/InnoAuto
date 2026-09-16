package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import java.util.List;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.params.provider.CsvFileSource;


public class TasksCheckTest {
    private static final Random RANDOM = new Random();
    @Test
    public void testFindMax(){
        int[]arr = {3, 17, 5};
        int expected =  17;
        int actual = Tasks.findMax(arr);
        if (actual == expected){
            System.out.println("TEST PASSED: findMax returned " + actual);
        }
        else {
            System.out.println("TEST FAILED: expected " + expected + ", got " + actual);
        }
    }

    @Test
    public void testHasBug(){
        String[] messages = {"All good", "BUG", "Moving on"};
        boolean expected = true;
        boolean actual = Tasks.hasBug(messages);

        if (actual == expected){
            System.out.println("TEST PASSED: hasBug returned " + actual);
        } else {
            System.out.println("TEST FAILED: expected " + expected + ", got " + actual);
        }
    }

    @Test
    public void testReverse(){
        String[] input = {"One", "Two", "Zero"};
        String[] expected = {"Zero", "Two", "One"};
        String[] actual = Tasks.reverse(input);
        if (Arrays.equals(actual, expected)) {
            System.out.println("TEST PASSED: reverse returned " + Arrays.toString(actual));
        } else {
            System.out.println("TEST FAILED: expected " + Arrays.toString(expected)
                    + ", got " + Arrays.toString(actual));
        }
    }

    @Test
    public void testRemoveSpecificName() {
        List<String> names = List.of("Alice", "Mary", "Igor");           // исходный список имён
        List<String> expected = List.of("Alice", "Igor");                // ждём тот же список, но без Mary
        List<String> actual = Tasks.removeSpecificName(names, "Mary");   // убираем Mary

        if (expected.equals(actual)) {                                   // списки сравниваем через .equals()
            System.out.println("TEST PASSED: removeSpecificName returned " + actual);
        } else {
            System.out.println("TEST FAILED: expected " + expected + ", got " + actual);
        }
    }

    @RepeatedTest(10)
    public void testIsEven() {
        int n = RANDOM.nextInt(100) + 1;
        boolean expected = (n % 2 == 0);
        boolean actual = Tasks.isEven(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: isEven(" + n + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: isEven(" + n + ") expected " + expected + ", got " + actual);
        }
    }

    @RepeatedTest(10)
    public void testIsPositive() {
        int n = RANDOM.nextInt(201) - 100;
        boolean expected = (n >= 0);
        boolean actual = Tasks.isPositive(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: isPositive(" + n + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: isPositive(" + n + ") expected " + expected + ", got " + actual);
        }
    }

    @RepeatedTest(10)
    public void testSumToN() {
        int n = RANDOM.nextInt(100) + 1;
        int expected = n * (n + 1) / 2;
        int actual = Tasks.sumToN(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: sumToN(" + n + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: sumToN(" + n + ") expected " + expected + ", got " + actual);
        }
    }

    @RepeatedTest(10)
    public void testCalcAverage() {
        int value = RANDOM.nextInt(100) + 1;
        int size = RANDOM.nextInt(5) + 1;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(value);
        }

        double expected = value;
        double actual = Tasks.calcAverage(numbers);

        if (actual == expected) {
            System.out.println("TEST PASSED: calcAverage(" + numbers + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: calcAverage(" + numbers + ") expected " + expected + ", got " + actual);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "19, Allowed",
            "18, Denied",
            "0, Denied",
    })
    public void testCheckAccess(int age, String expected) {
        String actual = Tasks.checkAccess(age);

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED: checkAccess(" + age + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: checkAccess(" + age + ") expected " + expected + ", got " + actual);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "5, '5 4 3 2 1 Поехали!'",
            "3, '3 2 1 Поехали!'",
            "1, '1 Поехали!'"
    })
    public void testBlastOff(int start, String expected) {
        String actual = Tasks.blastOff(start);

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED: blastOff(" + start + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: blastOff(" + start + ") expected [" + expected + "], got [" + actual + "]");
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/grades.csv", numLinesToSkip = 1)
    public void testGetGrade(int score, String expected) {
        String actual = Tasks.getGrade(score);

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED: getGrade(" + score + ") returned " + actual);
        } else {
            System.out.println("TEST FAILED: getGrade(" + score + ") expected " + expected + ", got " + actual);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/even-range.csv", numLinesToSkip = 1)
    public void testGetEvenInRange(int start, int end, String expected) {
        String actual = Tasks.getEvenInRange(start, end);

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED: getEvenInRange(" + start + ", " + end + ") returned [" + actual + "]");
        } else {
            System.out.println("TEST FAILED: getEvenInRange(" + start + ", " + end + ") expected ["
                    + expected + "], got [" + actual + "]");
        }
    }
}

