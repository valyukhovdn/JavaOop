package ru.academits.valyukhov.shape;

public class Triangle implements Shape {
    private static final String shapeName = "Треугольник";
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    public double getWidth() {
        return Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
    }

    public double getHeight() {
        return Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
    }

    public double getArea() {
        return Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) / 2;
    }

    public double getPerimeter() {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) +
                Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2)) +
                Math.sqrt((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1));
    }

    @Override
    public String toString() {
        return String.format("%-14s: x1 - %.2f, y1 - %.2f, x2 - %.2f, y2 - %.2f, x3 - %.2f, y3 - %.2f, площадь - %7.2f, " +
                        "периметр - %7.2f;", shapeName, this.x1, this.y1, this.x2, this.y2, this.x3, this.y3,
                getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = hash * prime + Double.hashCode(x1);
        hash = hash * prime + Double.hashCode(y1);
        hash = hash * prime + Double.hashCode(x2);
        hash = hash * prime + Double.hashCode(y2);
        hash = hash * prime + Double.hashCode(x3);
        hash = hash * prime + Double.hashCode(y3);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (hashCode() != o.hashCode()) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.getClass() != getClass()) {
            return false;
        }

        Triangle triangle = (Triangle) o;

        return x1 == triangle.x1 && y1 == triangle.y1 && x2 == triangle.x2 && y2 == triangle.y2 &&
                x3 == triangle.x3 && y3 == triangle.y3;
    }
}
