package ru.academits.valyukhov.ArrayListHome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListHome {
    //  1. ����� ������ � ������ ���� ����� �� �����.
    public static ArrayList<String> getArrayList(File file) throws FileNotFoundException {
        ArrayList<String> StringsList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextLine()) {
                StringsList.add(scanner.nextLine());
            }
        }

        return StringsList;
    }

    // 2. ����� �������� ������ ����� �� ������ �� �������.
    public static void removeEvenNumbers(ArrayList<Integer> integerList) {
        ArrayList<Integer> evenNumbersList = new ArrayList<>();

        for (Integer integer : integerList) {
            if (integer % 2 == 0) {
                evenNumbersList.add(integer);
            }
        }

        integerList.removeAll(evenNumbersList);
    }

    //  3. ����� �������� ������ ������, � ������� ����� �������� ����������� ������ � ����� �� �������, �� ��� ����������.
    public static ArrayList<Integer> listWithoutRepetition(ArrayList<Integer> integerList) {
        ArrayList<Integer> listWithoutRepetition = new ArrayList<>();
        listWithoutRepetition.ensureCapacity(integerList.size());

        for (int i = 0; i < integerList.size(); ++i) {
            boolean foundRepetition = false;

            for (int j = 0; j < i; ++j) {
                if (integerList.get(i).equals(integerList.get(j))) {
                    foundRepetition = true;
                    break;
                }
            }

            if (!foundRepetition) {
                listWithoutRepetition.add(integerList.get(i));
            }
        }

        return listWithoutRepetition;
    }
}
