package ru.academits.valyukhov.lambdasTask2;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdasTask2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Задача 2.1. Поток корней чисел.
        System.out.print("Введите необходимое количество элементов из потока корней целых чисел: ");
        int elementsQuantity = scanner.nextInt();

        if (elementsQuantity < 0) {
            System.out.printf("Ошибка! Надо было ввести не отрицательное число, а вы ввели \"%d\".", elementsQuantity);
            System.out.println();
            return;
        }

        DoubleStream squareRootsStream = IntStream.iterate(0, x -> x + 1).mapToDouble(Math::sqrt).limit(elementsQuantity);
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

        if (fibonacciNumbersQuantity == 0) {
            return;
        }

        Stream<Integer> fibonacciNumbers = Stream.concat(Stream.of(0), Stream
                .iterate(new NumbersPair(0, 1), NumbersPair::new)
                .map(NumbersPair::getValue2));

        fibonacciNumbers.limit(fibonacciNumbersQuantity)
                .forEach(number -> System.out.printf("%12d" + System.lineSeparator(), number));
    }
}