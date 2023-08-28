package ru.academits.valyukhov.ArrayListHome_main;

import ru.academits.valyukhov.ArrayListHome.ArrayListHome;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome_main {
    public static void main(String[] args) throws FileNotFoundException {
        //  1. „итаем в список все строки из файла.
        System.out.println("—писок строк из переданного файла:");

        for (String line : ArrayListHome.getArrayList(new File("ArrayListHome_input.txt"))) {
            System.out.println(line);
        }

        System.out.println();

        // 2. »з списка на массиве целых чисел оставл€ем только нечЄтные.
        System.out.println("»сходный список целых чисел:");
        ArrayList<Integer> integerList = new ArrayList<>(Arrays.asList
                (-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(integerList);
        System.out.println();

        System.out.println("»сходный список без чЄтных чисел:");
        ArrayListHome.removeEvenNumbers(integerList);
        System.out.println(integerList);

        //  3. —оздаЄм новый список, в котором будут элементы переданного списка в таком же пор€дке, но без повторений.
        System.out.println("”бираем из списка повтор€ющиес€ элементы:");
        System.out.println(ArrayListHome.listWithoutRepetition(integerList));
    }
}
