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
        int streamElementsQuantity = scanner.nextInt();

        if (streamElementsQuantity < 0) {
            System.out.printf("Ошибка! Надо было ввести не отрицательное число, а вы ввели \"%d\".", streamElementsQuantity);
            System.out.println();
            return;
        }

        DoubleStream squareRootsStream = IntStream.iterate(0, x -> x + 1).mapToDouble(Math::sqrt).limit(streamElementsQuantity);
        squareRootsStream.forEach(x -> System.out.printf("%7f" + System.lineSeparator(), x));

        System.out.println();

        // Задача 2.2. Поток чисел Фибоначчи.
        System.out.print("Введите требуемое количество чисел Фибоначчи: ");
        int fibonacciNumbersQuantity = scanner.nextInt();

        if (fibonacciNumbersQuantity < 0) {
            System.out.printf("Ошибка! Надо было ввести не отрицательное число, а вы ввели \"%d\".", fibonacciNumbersQuantity);
            System.out.println();
            return;
        }

        IntStream fibonacciNumbers = Stream.iterate(new int[]{0, 1}, array -> new int[]{array[1], array[0] + array[1]})
                .mapToInt(array -> array[0]);

        fibonacciNumbers.limit(fibonacciNumbersQuantity)
                .forEach(number -> {
                    System.out.printf("%12d", number);
                    System.out.println();
                });
    }
}