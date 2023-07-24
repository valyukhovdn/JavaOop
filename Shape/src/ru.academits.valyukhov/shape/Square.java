package ru.academits.valyukhov.shape;

public class Square implements Shape {
    private static final String shapeName = "Квадрат";
    private double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getWidth() {
        return sideLength;
    }

    public double getHeight() {
        return sideLength;
    }

    public double getArea() {
        return sideLength * sideLength;
    }

    public double getPerimeter() {
        return sideLength * 4;
    }

    @Override
    public String toString() {
        return String.format("%-14s: длина стороны - %-49.2f, площадь - %7.2f, периметр - %7.2f;",
                shapeName, this.sideLength, getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        return prime * hash + Double.hashCode(sideLength);
    }

    @Override
    public boolean equals(Object o) {
        if (hashCode() != o.hashCode()) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (o.getClass() != getClass()) {
            return false;
        }

        Square square = (Square) o;

        return sideLength == square.sideLength;
    }
}
