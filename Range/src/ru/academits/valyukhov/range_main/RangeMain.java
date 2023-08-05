package ru.academits.valyukhov.range_main;

import ru.academits.valyukhov.range.Range;

import java.util.Arrays;
import java.util.Scanner;

public class RangeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите начальное (наименьшее) вещественное число ПЕРВОГО диапазона: ");
        double from = scanner.nextDouble();

        System.out.print("Введите конечное (наибольшее) вещественное число ПЕРВОГО диапазона: ");
        double to = scanner.nextDouble();

        if (from > to) {
            System.out.println("Ошибка! Конечное число диапазона должно быть больше начального.");
            return;
        }

        Range range1 = new Range(from, to);
        System.out.println("ПЕРВЫЙ диапазон: " + range1);
        System.out.println("Длина ПЕРВОГО диапазона: " + range1.getLength());

        System.out.printf("%nВведите вещественное число, входящее в указанный диапазон " + range1 + ": ");
        double userNumber = scanner.nextDouble();

        if (range1.isInside(userNumber)) {
            System.out.printf("Правильно, введённое Вами число %f принадлежит указанному диапазону.%n", userNumber);
        } else {
            System.out.printf("Введённое Вами число %f находится вне указанного диапазона.%n%n", userNumber);
            System.out.printf("Теперь измените диапазон так, чтобы число %f входило в него.%n", userNumber);

            System.out.print("Введите новое начальное вещественное число ПЕРВОГО диапазона: ");
            range1.setFrom(scanner.nextDouble());

            System.out.print("Введите новое конечное вещественное число ПЕРВОГО диапазона: ");
            range1.setTo(scanner.nextDouble());

            if (range1.getFrom() > range1.getTo()) {
                System.out.println("ОШИБКА! Конечное число диапазона должно быть больше начального.");
                return;
            } else {
                if (range1.isInside(userNumber)) {
                    System.out.printf("Теперь ранее введённое Вами число %f принадлежит обновлённому ПЕРВОМУ диапазону.%n", userNumber);
                } else {
                    System.out.printf("Введённое Вами ранее число %f находится вне обновлённого ПЕРВОГО диапазона.%n", userNumber);
                    System.out.printf("Обратите внимание, Вы указали начало диапазона %f, конец диапазона %f, " +
                            "a ведённое Вами число имеет значение %f.%n", range1.getFrom(), range1.getTo(), userNumber);
                }
            }
        }

        System.out.println();

        System.out.print("Введите начальное (наименьшее) вещественное число ВТОРОГО диапазона: ");
        from = scanner.nextDouble();

        System.out.print("Введите конечное (наибольшее) вещественное число ВТОРОГО диапазона: ");
        to = scanner.nextDouble();

        if (from > to) {
            System.out.println("ОШИБКА! Конечное число диапазона должно быть больше начального.");
            return;
        }

        Range range2 = new Range(from, to);
        System.out.println("ВТОРОЙ диапазон: " + range2);
        System.out.println();

        // Пересечение двух интервалов.
        Range intersection = range1.getIntersection(range2);

        if (intersection == null) {
            System.out.println("Указанные диапазоны не пересекаются!");
        } else {
            System.out.print("Диапазон-пересечения ПЕРВОГО и ВТОРОГО диапазонов: ");
            System.out.println(intersection);
        }

        //  Объединение двух интервалов.
        Range[] union = range1.getUnion(range2);

        if (union.length == 1) {
            System.out.print("Объединением ПЕРВОГО и ВТОРОГО диапазонов является диапазон: ");
            System.out.println(Arrays.toString(union));
        } else {
            System.out.print("Объединением ПЕРВОГО и ВТОРОГО диапазонов являются 2 диапазона: ");
            System.out.println(Arrays.toString(union));
        }

        //  Разность между первым и вторым интервалом.
        Range[] difference = range1.getDifference(range2);

        if (difference.length == 2) {
            System.out.print("Разностью ПЕРВОГО и ВТОРОГО диапазонов являются 2 диапазона: ");
            System.out.println(Arrays.toString(difference));
        } else {
            System.out.print("Разностью ПЕРВОГО и ВТОРОГО диапазонов является диапазон: ");
            System.out.println(Arrays.toString(difference));
        }
    }
}
