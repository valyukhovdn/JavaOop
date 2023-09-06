package ru.academits.valyukhov.array_list_home;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    // 1. Метод чтения в список всех строк из файла.
    public static ArrayList<String> getLinesList(String path) {
        ArrayList<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String s;

            while ((s = reader.readLine()) != null) {
                linesList.add(s);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return linesList;
    }

    // 2. Метод удаления чётных чисел из списка на массиве.
    public static void removeEvenNumbers(ArrayList<Integer> integersList) {
        integersList.removeIf(integer -> integer % 2 == 0);
    }

    // 3. Метод создания нового списка, в котором будут элементы переданного списка в таком же порядке, но без повторений.
    public static ArrayList<Integer> getListWithoutRepetitions(ArrayList<Integer> integersList) {
        ArrayList<Integer> duplicateListWithoutRepetitions = new ArrayList<>(integersList.size());

        for (Integer integer : integersList) {
            if (!duplicateListWithoutRepetitions.contains(integer)) {
                duplicateListWithoutRepetitions.add(integer);
            }
        }

        return duplicateListWithoutRepetitions;
    }

    public static void main(String[] args) {
        // 1. Читаем в список все строки из файла.
        System.out.println("Список строк из переданного файла:");

        for (String line : getLinesList("ArrayListHome_input.txt")) {
            System.out.println(line);
        }

        System.out.println();

        // 2. Из списка на массиве целых чисел оставляем только нечётные.
        System.out.println("Исходный список целых чисел:");
        ArrayList<Integer> integersList = new ArrayList<>
                (Arrays.asList(-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(integersList);
        System.out.println();

        System.out.println("Исходный список без чётных чисел:");
        removeEvenNumbers(integersList);
        System.out.println(integersList);
        System.out.println();

        // 3. Создаём новый список, в котором будут элементы переданного списка в таком же порядке, но без повторений.
        System.out.println("Убираем из списка повторяющиеся элементы:");
        System.out.println(getListWithoutRepetitions(integersList));
    }
}
