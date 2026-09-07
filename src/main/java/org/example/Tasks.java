package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tasks {

    //метод к Задаче №1
    public static boolean isEven(int n){
        return n % 2 ==0 ;
    }



    //метод к Задаче №2
    public static String checkAccess (int age){
        return age > 18 ? "Allowed" : "Denied";
    }



    //метод к Задаче №3
    public static boolean isPositive(int n){
        return n >=0 ? true : false;
    }

    //метод к Задаче №4
    public static String getGrade(int score){
        if (score>=0 && score<=20){return "E";}
        else if (score>=21 && score<=40) {return "D";}
        else if (score>=41 && score<=60){return "C";}
        else if (score>=61 && score<=80) {return "B";}
        else if (score>=81 && score<=100) {return "A";}
        else {return "Error";}
    }

    //метод к Задаче №5
    public static String blastOff(int start){
        String result = "";
        for (int i = start; i >=1; i--){result= result+ i +" ";}
        result = result + "Поехали!";
        return result;
    }

    //метод к Задаче №6
    public static int sumToN(int n){
        int sum = 0;
        for (int i=1; i<=n; i++){
            sum = sum + i;
        }
        return sum;
    }

    //метод к Задаче №7
    public static boolean hasBug(String[] messages){
        for (int i = 0; i < messages.length; i++){
            if (messages[i].equalsIgnoreCase("Bug")){return true;}
        }
        return false;
    }

    //метод к Задаче №8
    public static String getEvenInRange(int start, int end) {
        if (start > end) {return "";}
        String result = "";
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!result.isEmpty()) {
                    result = result + " ";
                }
                result = result + i;
            }
        }
        return result;
    }

    //метод к Задаче №9
    public static int findMax(int[] arr){
        if (arr == null || arr.length==0){throw new IllegalArgumentException("Ошбика!Пустой массив");}
        int max= arr[0];
        for (int i=1; i< arr.length;i++){
            if (arr[i]>max){
                max=arr[i];
            }
        }
        return max;
    }

    //метод к Задаче №10
    public static String[] reverse(String[] arr){
        if (arr==null ||arr.length==0){return arr;}
        String[] reversed = new String[arr.length];
        for (int i=0; i< arr.length; i++){
            reversed[i] = arr[arr.length - 1 - i];
        }
        return reversed;
    }

    //метод к Задаче №11
    public static double calcAverage (List<Integer>list){
        if (list==null || list.isEmpty()){
            return 0.0;
        }
        int sum=0;
        for (Integer num :list){
            sum+=num;
        }
        return (double) sum/list.size();
    }

    //метод к Задаче №12
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (String name: list){
            if (!name.equals(nameToRemove)){
                result.add(name);
            }
        }
        return result;
    }





    public static void main (String[] args){
        System.out.println("Задача №1 проверка 1 = " + isEven(10));
        System.out.println("Задача №1 проверка 2 = " + isEven(99));
        System.out.println("Задача №2 проверка 1 = " + checkAccess(99));
        System.out.println("Задача №2 проверка 2 = " + checkAccess(17));
        System.out.println("Задача №3 проверка 1 = " + isPositive(17));
        System.out.println("Задача №3 проверка 2 = " + isPositive(-17));
        System.out.println("Задача №4 проверка 1 = " + getGrade(-17));
        System.out.println("Задача №4 проверка 2 = " + getGrade(10));
        System.out.println("Задача №4 проверка 3 = " + getGrade(40));
        System.out.println("Задача №4 проверка 4 = " + getGrade(42));
        System.out.println("Задача №4 проверка 5 = " + getGrade(61));
        System.out.println("Задача №4 проверка 6 = " + getGrade(100));
        System.out.println("Задача №4 проверка 7 = " + getGrade(234));
        System.out.println("Задача №5 проверка 1 = " + blastOff(10));
        System.out.println("Задача №6 проверка 1 = " + sumToN(100));
        String[] test1 = {"Word","phoEnix","java"};
        System.out.println("Задача №7 проверка 1 = " + hasBug(test1));
        String[] test2 = {"Word","phoEnix","bug"};
        System.out.println("Задача №7 проверка 2 = " + hasBug(test2));
        String[] test3 = {"Word","phoEnix","BUG"};
        System.out.println("Задача №7 проверка 2 = " + hasBug(test3));
        System.out.println("Задача №8 проверка 1 = " + getEvenInRange(10,30));
        int[] test4 = {1,4,6,9,35,45,23};
        System.out.println("Задача №9 проверка 1 = " + findMax(test4));
        String[] straight= {"One","Two","Three"};
        String[] reversed = reverse(straight);
        System.out.println("Задача №10 проверка 1. обычный массив: "+ Arrays.toString(straight));
        System.out.println("Задача №10 проверка 2. обратный массив: "+ Arrays.toString(reversed));
        List<Integer>numbers = List.of(1,2,3,4,5);
        System.out.println("Задача №11 проверка 1 = " + calcAverage(numbers));
        List <String> names = List.of("Alice","Mary","Igor","Poly","Eva");
        System.out.println("Задача №12 проверка 1. Полный список: " + names);
        System.out.println("После удаления 'Bob': " + removeSpecificName(names, "Mary"));
    }
}

