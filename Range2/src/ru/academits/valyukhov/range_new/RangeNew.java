package ru.academits.valyukhov.range_new;

public class RangeNew {
    private double from;
    private double to;

    public RangeNew(double from, double to) {
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

    //  Определение интервала-пересечения двух интервалов.
    public RangeNew getIntersection(RangeNew interval1, RangeNew interval2) {
        if (interval1.to <= interval2.from || interval2.to <= interval1.from) {  //  Проверка на отсутствие пересечений.
            return null;
        } else if (interval2.from <= interval1.from && interval1.to <= interval2.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return interval1;
        } else if (interval1.from <= interval2.from && interval2.to <= interval1.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return interval2;
        } else if (interval1.from <= interval2.from) {  // Проверяем, какой интервал начинается раньше.
            return new RangeNew(interval2.from, interval1.to);
        } else
            return new RangeNew(interval1.from, interval2.to);
    }

    //  Определение объединения двух интервалов.
    public RangeNew[] getUnion(RangeNew interval1, RangeNew interval2) {
        if (interval1.to <= interval2.from || interval2.to <= interval1.from) {  //  Проверка на отсутствие пересечений.
            return new RangeNew[]{interval1, interval2};
        } else if (interval2.from <= interval1.from && interval1.to <= interval2.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return new RangeNew[]{interval2};
        } else if (interval1.from <= interval2.from && interval2.to <= interval1.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return new RangeNew[]{interval1};
        } else if (interval1.from <= interval2.from) {  // Проверяем, какой интервал начинается раньше.
            return new RangeNew[]{new RangeNew(interval1.from, interval2.to)};
        } else {
            return new RangeNew[]{new RangeNew(interval2.from, interval1.to)};
        }
    }

    //  Определение разности между первым и вторым интервалом.
    public RangeNew[] getDifference(RangeNew interval1, RangeNew interval2) {
        if (interval1.to <= interval2.from || interval2.to <= interval1.from) {  //  Проверка на отсутствие пересечений.
            return new RangeNew[]{interval1};
        } else if (interval2.from <= interval1.from && interval1.to <= interval2.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return new RangeNew[0];
        } else if (interval1.from <= interval2.from && interval2.to <= interval1.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return new RangeNew[]{new RangeNew(interval1.from, interval2.from), new RangeNew(interval2.to, interval1.to)};
        } else if (interval1.from <= interval2.from) {  // Проверяем, какой интервал начинается раньше.
            return new RangeNew[]{new RangeNew(interval1.from, interval2.from)};
        } else {
            return new RangeNew[]{new RangeNew(interval2.to, interval1.to)};
        }
    }
}

