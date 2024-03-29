package ru.academits.valyukhov.shapes_main;

import ru.academits.valyukhov.shapes.*;
import ru.academits.valyukhov.shapes_comparators.ShapesAreaComparator;
import ru.academits.valyukhov.shapes_comparators.ShapesPerimeterComparator;

import java.util.Arrays;

public class ShapesMain {
    private static Shape getMaxAreaShape(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Переданный массив фигур пуст!");
        }

        //  Сортируем массив фигур по возрастанию ПЛОЩАДЕЙ.
        Arrays.sort(shapes, new ShapesAreaComparator());

        return shapes[shapes.length - 1];
    }

    private static Shape getSecondPerimeterShape(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Переданный массив фигур пуст!");
        }

        if (shapes.length == 1) {
            throw new IllegalArgumentException("Переданный массив фигур содержит только один объект!");
        }

        //  Сортируем массив фигур по возрастанию ПЕРИМЕТРОВ.
        Arrays.sort(shapes, new ShapesPerimeterComparator());

        // Возвращаем фигуру со вторым по величине ПЕРИМЕТРОМ (предпоследняя в массиве).
        return shapes[shapes.length - 2];
    }

    public static void main(String[] args) {
        //  Инициализируем 4 пары фигур одного класса с РАЗНЫМИ значениями соответствующих полей:
        Circle circle1 = new Circle(7);
        Circle circle2 = new Circle(10.5);
        Rectangle rectangle1 = new Rectangle(8, 7);
        Rectangle rectangle2 = new Rectangle(1.5, 30);
        Square square1 = new Square(15);
        Square square2 = new Square(4);
        Triangle triangle1 = new Triangle(2, 2, 6, 4, 15, 5);
        Triangle triangle2 = new Triangle(4, 2, 4, 6, 12, 6);

        //  Создаём массив инициализированных фигур.
        Shape[] shapes = {circle1, circle2, rectangle1, rectangle2, square1, square2, triangle1, triangle2};

        //  Печатаем в консоль исходный массив фигур.
        System.out.println("Исходный массив фигур:");

        for (Shape shape : shapes) {
            System.out.println(shape);
        }

        System.out.println();

        //  Печатаем в консоль фигуру с максимальной ПЛОЩАДЬЮ.
        System.out.println("Фигура с максимальной ПЛОЩАДЬЮ:");
        System.out.println(getMaxAreaShape(shapes));
        System.out.println();

        //  Печатаем в консоль фигуру со вторым по величине ПЕРИМЕТРОМ.
        System.out.println("Фигура со вторым по величине ПЕРИМЕТРОМ:");
        System.out.println(getSecondPerimeterShape(shapes));
        System.out.println();

        // Проверки
        System.out.println("Проверка переопределения equals:");
        System.out.println();
        System.out.println("Проверяем на равенство фигуры одного класса с РАЗНЫМИ значениями соответствующих полей:");
        System.out.println("Равенство окружностей:     " + circle1.equals(circle2));        //  false
        System.out.println("Равенство прямоугольников: " + rectangle1.equals(rectangle2));  //  false
        System.out.println("Равенство квадратов:       " + square1.equals(square2));        //  false
        System.out.println("Равенство треугольников:   " + triangle1.equals(triangle2));    //  false
        System.out.println();

        //  Пары фигур одного класса с ОДИНАКОВЫМИ значениями соответствующих полей:
        Circle circle3 = new Circle(7);
        Circle circle4 = new Circle(7);
        Rectangle rectangle3 = new Rectangle(8, 7);
        Rectangle rectangle4 = new Rectangle(8, 7);
        Square square3 = new Square(15);
        Square square4 = new Square(15);
        Triangle triangle3 = new Triangle(2, 2, 6, 4, 15, 5);
        Triangle triangle4 = new Triangle(2, 2, 6, 4, 15, 5);

        System.out.println("Проверяем на равенство фигуры одного класса с ОДИНАКОВЫМИ значениями соответствующих полей:");
        System.out.println("Равенство окружностей:     " + circle3.equals(circle4));        //  true
        System.out.println("Равенство прямоугольников: " + rectangle3.equals(rectangle4));  //  true
        System.out.println("Равенство квадратов:       " + square3.equals(square4));        //  true
        System.out.println("Равенство треугольников:   " + triangle3.equals(triangle4));    //  true
        System.out.println();

        System.out.println("Проверка переопределения hashCode для пар однотипных фигур с РАЗНЫМИ значениями полей:");
        System.out.println(circle2.hashCode());
        System.out.println(circle3.hashCode());
        System.out.println(rectangle2.hashCode());
        System.out.println(rectangle3.hashCode());
        System.out.println(square2.hashCode());
        System.out.println(square3.hashCode());
        System.out.println(triangle2.hashCode());
        System.out.println(triangle3.hashCode());
        System.out.println();

        System.out.println("Проверка переопределения hashCode для пар однотипных фигур с ОДИНАКОВЫМИ значениями полей:");
        System.out.println(circle3.hashCode());
        System.out.println(circle4.hashCode());
        System.out.println(rectangle3.hashCode());
        System.out.println(rectangle4.hashCode());
        System.out.println(square3.hashCode());
        System.out.println(square4.hashCode());
        System.out.println(triangle3.hashCode());
        System.out.println(triangle4.hashCode());
        System.out.println();
    }
}
