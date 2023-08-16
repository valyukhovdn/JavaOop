package ru.academits.valyukhov.vector_main;

import ru.academits.valyukhov.vector.Vector;

public class VectorMain {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{1.2, 3.7, 5.8});

        System.out.println("\"vector1\": " + vector1);

        Vector vector2 = new Vector(new double[]{0.8, 0.3, 0.2, 0.64});

        System.out.println("\"vector2\": " + vector2);
        System.out.println();

        System.out.println("Результат прибавления (не static) к вектору \"vector1\" вектора \"vector2\":");
        vector1.add(vector2);
        System.out.println("\"vector1\" стал: " + vector1);
        System.out.println();

        System.out.println("Результат вычитания (не static) из вектора \"vector1\" вектора \"vector2\":");
        vector1.subtract(vector2);
        System.out.println("\"vector1\" стал: " + vector1);
        System.out.println();

        System.out.println("В результате сложения двух векторов (static) получаем новый вектор:");
        System.out.println(Vector.getSum(vector1, vector2));
        System.out.println();

        System.out.println("В результате вычитания двух векторов (static) получаем новый вектор:");
        System.out.println(Vector.getDifference(vector1, vector2));
        System.out.println();

        System.out.print("Скалярное произведение (static) векторов \"vector1\" и \"vector2\" равно:");
        System.out.printf("%10.2f%n", Vector.getScalarProduct(vector1, vector2));
        System.out.println();

        double scalar = 3.5;
        System.out.printf("Результат умножения вектора \"vector1\" на скаляр %6.2f:%n", scalar);
        vector1.multiplyByScalar(scalar);
        System.out.println("\"vector1\": " + vector1);
        System.out.println();

        System.out.println("Результат разворота (умножения всех компонент на -1) вектора \"vector1\":");
        vector1.reversal();
        System.out.println("\"vector1\" стал: " + vector1);
        System.out.println();

        System.out.printf("Длина вектора \"vector1\" равна: %10.2f%n", vector1.getLength());
        System.out.println();

        System.out.printf("Компонента с индексом \"1\" вектора \"vector2\" равна: %10.2f%n", vector2.getComponent(1));
        System.out.println();

        System.out.println("Теперь заменяем значение компоненты с индексом \"1\" вектора \"vector2\" на 12,34.");
        vector2.setComponent(1, 12.34);
        System.out.println("Вектор \"vector2\" имеет значения: " + vector2);
        System.out.println();

        System.out.println("Проверяем на равенство \"vector1\" и \"vector2\" через переопределённый equals:");
        System.out.print("\"vector1\" и \"vector2\" " + (vector1.equals(vector2) ? "равны." : "не равны."));
        System.out.println();
    }
}
