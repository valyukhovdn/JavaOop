package ru.academits.valyukhov.range_main;

import ru.academits.valyukhov.range.Range;

import java.util.Scanner;

public class RangeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите начальное (наименьшее) вещественное число диапазона: ");
        double from = scanner.nextDouble();

        System.out.print("Введите конечное (наибольшее) вещественное число диапазона: ");
        double to = scanner.nextDouble();

        if (from > to) {
            System.out.println("Ошибка! Конечное число диапазона должно быть больше начального.");
        } else {
            Range range = new Range(from, to);
            System.out.println("Длина указанного диапазона: " + range.getLength());

            System.out.printf("Введите вещественное число, входящее в указанный диапазон (от %f до %f): ", from, to);
            double userNumber = scanner.nextDouble();

            if (range.isInside(userNumber)) {
                System.out.printf("Правильно, введённое Вами число %f принадлежит указанну диапазону.", userNumber);
            } else {
                System.out.printf("Введённое Вами число %f находится вне указанного диапазона.%n", userNumber);
                System.out.printf("Теперь измените диапазон так, чтобы число %f входило в него.%n", userNumber);

                System.out.print("Введите новое начальное вещественное число диапазона: ");
                range.setFrom(scanner.nextDouble());

                System.out.print("Введите новое конечное вещественное число диапазона: ");
                range.setTo(scanner.nextDouble());

                if (range.isInside(userNumber)) {
                    System.out.printf("Теперь ранее введённое Вами число %f принадлежит новому диапазону.", userNumber);
                } else {
                    System.out.printf("Введённое Вами ранее число %f находится вне нового диапазона.%n", userNumber);
                    System.out.printf("Обратите внимание, Вы указали начало диапазона %f, конец диапазона %f, " +
                            "a ведённое Вами число имеет значение %f.", range.getFrom(), range.getTo(), userNumber);
                }
            }
        }
    }
}
