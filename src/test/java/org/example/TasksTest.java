package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;

import java.util.stream.Stream;


public class TasksTest {
    private static final Random RANDOM = new Random();
    @BeforeEach
    public void beforeEachTest(){
        System.out.println("========================");
        System.out.println("Test method start");
    }

    @AfterEach
    public void afterEachTest(){
        System.out.println("Test method end");
        System.out.println("========================");
    }

    @Test
    public void testIsEven(){
        int n = RANDOM.nextInt(100) + 1;
        boolean result = Tasks.isEven(n);
        System.out.println("isEven(" + n + ") = " + result);

    }

    @RepeatedTest(20)
    public void testCheckAccess(){
        int age = RANDOM.nextInt(100);
        String result = Tasks.checkAccess(age);
        System.out.println("checkAccess(" + age + ")= " + result);
    }

    @ParameterizedTest

    @MethodSource("randomScores")
    public void testGetGrade(int score){
        String result = Tasks.getGrade(score);
        System.out.println("getGrade(" + score + ") = " + result);
    }
    static Stream<Integer> randomScores() {
        return Stream.generate(() -> RANDOM.nextInt(101))
                .limit(10);
    }

}

