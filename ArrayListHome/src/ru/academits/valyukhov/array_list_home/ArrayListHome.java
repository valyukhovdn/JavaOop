package ru.academits.valyukhov.array_list_home;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    // 1. Метод чтения в список всех строк из файла.
    public static ArrayList<String> getLinesFromFile(String filePath) throws IOException {
        ArrayList<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                linesList.add(line);
            }
        }

        return linesList;
    }

    // 2. Метод удаления чётных чисел из списка на массиве.
    public static void removeEvenNumbers(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
                --i;
            }
        }
    }

    // 3. Метод создания нового списка, в котором будут элементы переданного списка в таком же порядке, но без повторений.
    public static ArrayList<Integer> getListWithoutDuplicates(ArrayList<Integer> list) {
        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(list.size());

        for (Integer integer : list) {
            if (!listWithoutDuplicates.contains(integer)) {
                listWithoutDuplicates.add(integer);
            }
        }

        return listWithoutDuplicates;
    }

    public static void main(String[] args) throws IOException {
        // 1. Читаем в список все строки из файла.
        try {
            ArrayList<String> linesFromFile = getLinesFromFile("C:\\Users\\User\\IdeaProjects\\JavaOop\\ArrayListHome_input.txt");

            System.out.println("Список строк из переданного файла:");

            for (String line : linesFromFile) {
                System.out.println(line);
            }

            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
            System.out.println();
        }

        // 2. Из списка на массиве целых чисел оставляем только нечётные.
        System.out.println("Исходный список целых чисел:");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(list);
        System.out.println();

        System.out.println("Исходный список без чётных чисел:");
        removeEvenNumbers(list);
        System.out.println(list);
        System.out.println();

        // 3. Создаём новый список, в котором будут элементы переданного списка в таком же порядке, но без повторений.
        System.out.println("Создаём новый список c теми же элементами, в том же порядке, но без повторений:");
        System.out.println(getListWithoutDuplicates(list));
    }
}
