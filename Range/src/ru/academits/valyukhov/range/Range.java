package ru.academits.valyukhov.range;

import java.util.Arrays;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    @Override
    public String toString() {
        return "(" + from + "; " + to + ")";
    }

    public static void print(Range[] ranges) {
        System.out.println(Arrays.toString(ranges));
    }

    //  Определение диапазона-пересечения двух интервалов.
    public Range getIntersection(Range range) {
        if (to <= range.from || range.to <= from) {  //  Проверка на отсутствие пересечений.
            return null;
        }

        return new Range(Math.max(from, range.from), Math.min(to, range.to));
    }

    //  Определение объединения двух диапазонов.
    public Range[] getUnion(Range range) {
        if (to < range.from || range.to < from) {  //  Проверка на отсутствие пересечений.
            return new Range[]{this, range};
        }

        return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
    }

    //  Определение разности между первым и вторым диапазоном.
    public Range[] getDifference(Range range) {
        if (to <= range.from || range.to <= from) {  //  Проверка на отсутствие пересечений.
            return new Range[]{new Range(from, to)};
        }

        if (range.from <= from && to <= range.to) {  //  Проверяем, входит ли 1-й диапазон во 2-й.
            return new Range[]{};
        }

        if (from <= range.from && range.to <= to) {  //  Проверяем, входит ли 2-й диапазон в 1-й.
            return new Range[]{
                    new Range(from, range.from),
                    new Range(range.to, to)
            };
        }

        if (from <= range.from) {  // Проверяем, какой диапазон начинается раньше.
            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(range.to, to)};
    }
}
