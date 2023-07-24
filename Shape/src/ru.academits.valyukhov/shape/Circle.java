package ru.academits.valyukhov.shape;

public class Circle implements Shape {
    private static final String shapeName = "Окружность";
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getWidth() {
        return radius * 2;
    }

    public double getHeight() {
        return radius * 2;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("%-14s: радиус - %-56.2f, площадь - %7.2f, периметр - %7.2f;", shapeName,
                this.radius, getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        return hash * prime + Double.hashCode(radius);
    }

    @Override
    public boolean equals(Object o) {
        if (o.hashCode() != hashCode()) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.getClass() != getClass()) {
            return false;
        }

        Circle circle = (Circle) o;

        return radius == circle.radius;
    }
}
