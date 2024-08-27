package ru.academits.valyukhov;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class NumbersPair {
    int value1;
    int value2;

    public NumbersPair(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public NumbersPair(NumbersPair pair) {
        value1 = pair.getValue2();
        value2 = pair.getValue1() + value1;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}

public class LambdasTask2 {
    public static void main(String[] args) {
        // Задача 2.1. Поток корней чисел.
        System.out.print("Введите необходимое количество элементов из потока корней целых чисел: ");

        Scanner scanner = new Scanner(System.in);

        int elementsNumber = scanner.nextInt();

        if (elementsNumber <= 0) {
            System.out.printf("Ошибка! Надо было ввести число больше нуля, а вы ввели \"%d\".\n", elementsNumber);
            return;
        }

        DoubleStream squareRootsStream = IntStream.iterate(1, x -> x + 1).mapToDouble(Math::sqrt).limit(elementsNumber);
        squareRootsStream.forEach(x -> System.out.printf("%7f\n", x));

        System.out.println();

        // Задача 2.2. Поток чисел Фибоначчи.
        System.out.print("Введите требуемое количество чисел Фибоначчи: ");

        int fibonacciNumbersQuantity = scanner.nextInt();

        if (fibonacciNumbersQuantity <= 0) {
            System.out.printf("Ошибка! Надо было ввести число больше нуля, а вы ввели \"%d\".\n", fibonacciNumbersQuantity);
            return;
        }

        Stream<Integer> zeroStream = Stream.of(0);

        NumbersPair initialPair = new NumbersPair(0, 1);

        Stream<Integer> fibonacciNumbersWithoutZeroStream = Stream
                .iterate(initialPair, NumbersPair::new)
                .map(NumbersPair::getValue2)
                .limit(fibonacciNumbersQuantity - 1);

        Stream.concat(zeroStream, fibonacciNumbersWithoutZeroStream)
              .forEach(number -> System.out.printf("%7d\n", number));
    }
}