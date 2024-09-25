package ru.academits.valyukhov.lambdas_task2;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdasTask2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Задача 2.1. Поток корней чисел.
        System.out.print("Введите необходимое количество элементов из потока корней целых чисел: ");
        int squareRootsQuantity = scanner.nextInt();

        if (squareRootsQuantity < 0) {
            System.out.printf("Ошибка! Надо было ввести не отрицательное число, а вы ввели \"%d\".%n", squareRootsQuantity);
            return;
        }

        DoubleStream squareRootsStream = IntStream.iterate(0, x -> x + 1).mapToDouble(Math::sqrt).limit(squareRootsQuantity);
        squareRootsStream.forEach(x -> System.out.printf("%7f%n", x));

        System.out.println();

        // Задача 2.2. Поток чисел Фибоначчи.
        System.out.print("Введите требуемое количество чисел Фибоначчи: ");
        int fibonacciNumbersQuantity = scanner.nextInt();

        if (fibonacciNumbersQuantity < 0) {
            System.out.printf("Ошибка! Надо было ввести не отрицательное число, а вы ввели \"%d\".%n", fibonacciNumbersQuantity);
            return;
        }

        IntStream fibonacciNumbersStream = Stream.iterate(new int[]{0, 1}, array -> new int[]{array[1], array[0] + array[1]})
                .mapToInt(array -> array[0]);

        fibonacciNumbersStream.limit(fibonacciNumbersQuantity)
                .forEach(number -> System.out.printf("%12d%n", number));
    }
}