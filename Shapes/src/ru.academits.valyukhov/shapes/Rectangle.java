package ru.academits.valyukhov.shapes;

public class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return (width + height) * 2;
    }

    @Override
    public String toString() {
        return String.format("%-14s: ширина - %-7.2f, высота - %-38.2f, площадь - %7.2f, периметр - %7.2f;",
                "Прямоугольник", width, height, getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = hash * prime + Double.hashCode(width);
        hash = hash * prime + Double.hashCode(height);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Rectangle rectangle = (Rectangle) o;

        return width == rectangle.width && height == rectangle.height;
    }
}
