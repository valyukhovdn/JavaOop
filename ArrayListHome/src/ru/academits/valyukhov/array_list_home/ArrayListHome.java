package ru.academits.valyukhov.array_list_home;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    // 1. ����� ������ � ������ ���� ����� �� �����.
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

    // 2. ����� �������� ������ ����� �� ������ �� �������.
    public static void removeEvenNumbers(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
                --i;
            }
        }
    }

    // 3. ����� �������� ������ ������, � ������� ����� �������� ����������� ������ � ����� �� �������, �� ��� ����������.
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
        // 1. ������ � ������ ��� ������ �� �����.
        System.out.println("������ ����� �� ����������� �����:");

        try {
            for (String line : getLinesFromFile("ArrayListHome_input.txt")) {
                System.out.println(line);
            }

            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("�������� ������: " + e.getMessage());
        }

        // 2. � ������ �� ������� ����� ����� ��������� ������ ��������.
        System.out.println("������� ������ ����� �����:");
        ArrayList<Integer> list = new ArrayList<>(
                Arrays.asList(-2, -2, -1, 0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10));
        System.out.println(list);
        System.out.println();

        System.out.println("������� ������ ��� ������ �����:");
        removeEvenNumbers(list);
        System.out.println(list);
        System.out.println();

        // 3. ������ ����� ������, � ������� ����� �������� ����������� ������ � ����� �� �������, �� ��� ����������.
        System.out.println("������ ����� ������ c ���� �� ����������, � ��� �� �������, �� ��� ����������:");
        System.out.println(getListWithoutDuplicates(list));
    }
}
