package ru.academits.valyukhov.range;

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

    public static void printResult(Range[] range) {
        System.out.print("[");

        int i = 0;

        for (Range e : range) {
            if (i == 1) {
                System.out.print(", ");
            }

            System.out.print(e);

            ++i;

        }

        System.out.println("]");
    }


    //  Определение интервала-пересечения двух интервалов.
    public Range[] getIntersection(Range range) {
        if (this.to <= range.from || range.to <= this.from) {  //  Проверка на отсутствие пересечений.
            return new Range[]{null};
        }

        if (range.from <= this.from && this.to <= range.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return new Range[]{new Range(this.from, this.to)};             // Новый объект с диапазоном range1
        }

        if (this.from <= range.from && range.to <= this.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return new Range[]{new Range(range.from, range.to)};             // Новый объект с диапазоном range2
        }

        if (this.from <= range.from) {  // Проверяем, какой интервал начинается раньше.
            return new Range[]{new Range(range.from, this.to)};
        }

        return new Range[]{new Range(this.from, range.to)};
    }

    //  Определение объединения двух интервалов.
    public Range[] getUnion(Range range) {
        if (this.to < range.from || range.to < this.from) {  //  Проверка на отсутствие пересечений.
            return new Range[]{this, range};
        }

        if (range.from <= this.from && this.to <= range.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return new Range[]{range};
        }

        if (this.from <= range.from && range.to <= this.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return new Range[]{this};
        }

        if (this.from <= range.from) {  // Проверяем, какой интервал начинается раньше.
            return new Range[]{new Range(this.from, range.to)};
        }

        return new Range[]{new Range(range.from, this.to)};
    }

    //  Определение разности между первым и вторым интервалом.
    public Range[] getDifference(Range range) {
        if (this.to <= range.from || range.to <= this.from) {  //  Проверка на отсутствие пересечений.
            return new Range[]{this};
        }

        if (range.from <= this.from && this.to <= range.to) {  //  Проверяем, входит ли 1-й интервал во 2-й.
            return new Range[]{};
        }

        if (this.from <= range.from && range.to <= this.to) {  //  Проверяем, входит ли 2-й интервал в 1-й.
            return new Range[]{
                    new Range(this.from, range.from),
                    new Range(range.to, this.to)
            };
        }

        if (this.from <= range.from) {  // Проверяем, какой интервал начинается раньше.
            return new Range[]{new Range(this.from, range.from)};
        }

        return new Range[]{new Range(range.to, this.to)};
    }
}
