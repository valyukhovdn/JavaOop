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
    public static void removeEvenNumbers(ArrayList<Integer> integerArrayList) {
        for (int i = 0; i < integerArrayList.size(); ++i) {
            if (integerArrayList.get(i) % 2 == 0) {
                integerArrayList.remove(i);
                --i;
            }
        }
    }

    // 3. Метод создания нового списка, в котором будут элементы переданного списка в таком же порядке, но без повторений.
    public static ArrayList<Integer> getIntegerArrayListWithoutDuplicates(ArrayList<Integer> integerArrayList) {
        ArrayList<Integer> integerArrayListWithoutDuplicates = new ArrayList<>(integerArrayList.size());

        for (Integer integer : integerArrayList) {
            if (!integerArrayListWithoutDuplicates.contains(integer)) {
                integerArrayListWithoutDuplicates.add(integer);
            }
        }

        return integerArrayListWithoutDuplicates;
    }

    public static void main(String[] args) {
        // 1. Читаем в список все строки из файла.
        try {
            String filePath = "C:\\Users\\User\\IdeaProjects\\JavaOop\\ArrayListHome_input.txt";

            ArrayList<String> linesFromFile = getLinesFromFile(filePath);

            System.out.println("Список строк из переданного файла:");

            for (String line : linesFromFile) {
                System.out.println(line);
            }

            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Отсутствует файл, из которого требуется прочитать строки!");
            System.out.println(e.getMessage());
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        // 2. Из списка на массиве целых чисел оставляем только нечётные.
        System.out.println("Исходный список целых чисел:");
        ArrayList<Integer> integerArrayList = new ArrayList<>(Arrays.asList(-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(integerArrayList);
        System.out.println();

        System.out.println("Исходный список без чётных чисел:");
        removeEvenNumbers(integerArrayList);
        System.out.println(integerArrayList);
        System.out.println();

        // 3. Создаём новый список, в котором будут элементы переданного списка в таком же порядке, но без повторений.
        System.out.println("Создаём новый список c теми же элементами, в том же порядке, но без повторений:");
        System.out.println(getIntegerArrayListWithoutDuplicates(integerArrayList));
    }
}
