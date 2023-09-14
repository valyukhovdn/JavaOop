package ru.academits.valyukhov.array_list_home;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    // 1. Метод чтения в список всех строк из файла.
    public static ArrayList<String> getLinesList(String filePath) throws IOException {
        ArrayList<String> linesList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            linesList.add(line);
        }

        return linesList;
    }

    // 2. Метод удаления чётных чисел из списка на массиве.
    public static void removeEvenNumbers(ArrayList<Integer> integersList) {
        for (int i = 0; i < integersList.size(); ++i) {
            if (integersList.get(i) % 2 == 0) {
                integersList.remove(i);
                --i;
            }
        }
    }

    // 3. Метод создания нового списка, в котором будут элементы переданного списка в таком же порядке, но без повторений.
    public static ArrayList<Integer> getListWithoutDuplicates(ArrayList<Integer> integersList) {
        ArrayList<Integer> ListWithoutDuplicates = new ArrayList<>(integersList.size());

        for (Integer integer : integersList) {
            if (!ListWithoutDuplicates.contains(integer)) {
                ListWithoutDuplicates.add(integer);
            }
        }

        return ListWithoutDuplicates;
    }

    public static void main(String[] args) {
        // 1. Читаем в список все строки из файла.
        System.out.println("Список строк из переданного файла:");

        try {
            for (String line : getLinesList("ArrayListHome_input.txt")) {
                System.out.println(line);
            }

            System.out.println();
        } catch (IOException e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
        }

        // 2. Из списка на массиве целых чисел оставляем только нечётные.
        System.out.println("Исходный список целых чисел:");
        ArrayList<Integer> integersList = new ArrayList<>(
                Arrays.asList(-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(integersList);
        System.out.println();

        System.out.println("Исходный список без чётных чисел:");
        removeEvenNumbers(integersList);
        System.out.println(integersList);
        System.out.println();

        // 3. Создаём новый список, в котором будут элементы переданного списка в таком же порядке, но без повторений.
        System.out.println("Убираем из списка повторяющиеся элементы:");
        System.out.println(getListWithoutDuplicates(integersList));
    }
}
