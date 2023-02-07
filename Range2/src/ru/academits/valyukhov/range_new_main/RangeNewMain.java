package ru.academits.valyukhov.range_new_main;

import ru.academits.valyukhov.range_new.RangeNew;

import java.util.Scanner;

public class RangeNewMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите начало первого интервала (interval1.from): ");
        double from = scanner.nextDouble();

        System.out.print("Введите конец первого интервала (interval1.to): ");
        double to = scanner.nextDouble();

        RangeNew interval1 = new RangeNew(from, to);

        System.out.println("Первый интервал: " + interval1.getFrom() + " - " + interval1.getTo());
        System.out.println();

        System.out.print("Введите начало второго интервала (interval2.from): ");
        from = scanner.nextDouble();

        System.out.print("Введите конец первого интервала (interval2.to): ");
        to = scanner.nextDouble();

        RangeNew interval2 = new RangeNew(from, to);

        System.out.println("Второй интервал: " + interval2.getFrom() + " - " + interval2.getTo());
        System.out.println();

        // Пересечение двух интервалов.
        RangeNew intersection = interval1.getIntersection(interval1, interval2);

        if (intersection == null) {
            System.out.println("Указанные интервалы не пересекаются!");
        } else {
            System.out.println("Интервал-пересечения: (" + intersection.getFrom()
                    + " - " + intersection.getTo() + ")");
        }

        //  Объединение двух интервалов.
        RangeNew[] union = interval1.getUnion(interval1, interval2);
        if (union.length == 1) {
            System.out.println("Объединением является интервал: (" + union[0].getFrom() + " - " +
                    union[0].getTo() + ")");
        } else {
            System.out.println("Объединением являются 2 интервала: (" + union[0].getFrom() + " - " +
                    union[0].getTo() + ") и (" + union[1].getFrom() + " - " +
                    union[1].getTo() + ")");
        }

        //  Разность между первым и вторым интервалом.
        RangeNew[] difference = interval1.getDifference(interval1, interval2);
        if (difference.length == 0) {
            System.out.println("Разность интервалов равна: 0");
        } else if (difference.length == 1) {
            System.out.println("Разностью является интервал: (" + difference[0].getFrom() + " - " +
                    difference[0].getTo() + ")");
        } else {
            System.out.println("Разностью являются 2 интервала: (" + difference[0].getFrom() + " - " +
                    difference[0].getTo() + ") и (" + difference[1].getFrom() + " - " +
                    difference[1].getTo() + ")");
        }
    }
}
