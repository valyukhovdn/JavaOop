package ru.academits.valyukhov.arraylist_main;

import ru.academits.valyukhov.arraylist.ArrayList;

import java.util.Arrays;

public class ArrayListMain {
    public static void main(String[] args) {
        System.out.println("Создадим Список №1:");
        ArrayList<Integer> integersList1 = new ArrayList<>(10);

        for (int i = 1; i <= 8; ++i) {
            integersList1.add(i);
        }

        System.out.println(integersList1);
        System.out.println("Список №1 " + (integersList1.isEmpty() ? "пуст" : "НЕ пуст"));
        System.out.println("Количество элементов в Списке №1: " + integersList1.size());
        System.out.println();

        System.out.println("Создадим массив элементов аналогичный Списку №1:");
        System.out.println(Arrays.toString(integersList1.toArray()));
        System.out.println();

        System.out.println("Создадим массив целых чисел:");
        Integer[] integersArray = new Integer[15];

        for (int i = 15, j = 0; i > 0; i--, j++) {
            integersArray[j] = i;
        }

        System.out.println(Arrays.toString(integersArray));
        System.out.println("А теперь вставим в него значения Списка №1, воспользовавшись методом \"public <T> T[] toArray(T[] a)\"");
        System.out.println("Массив принял вид:");
        integersArray = integersList1.toArray(integersArray);
        System.out.println(Arrays.toString(integersArray));
        System.out.println();

        Integer itemForDelete = 4;
        System.out.println("Попытаемся удалить из Списка №1 элемент со значением \"" + itemForDelete + "\"");

        if (!integersList1.contains(itemForDelete)) {
            System.out.println("Удаление невозможно, т.к. такой элемент в Списке №1 отсутствует");
        } else {
            System.out.println("Такой элемент в Списке №1 содержится");

            if (integersList1.remove(itemForDelete)) {
                System.out.println("Элемент со значением \"" + itemForDelete + "\" успешно удалён, а Список №1 принял вид:");
                System.out.println(integersList1);
            }
        }

        System.out.println();

        Integer insertItem = null;
        int insertIndex = 6;
        System.out.println("Вставим в список элемент со значением \"" + insertItem + "\" по индексу \"" + insertIndex + "\"");
        integersList1.add(insertIndex, insertItem);
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        int itemIndex = 5;
        System.out.println("Элемент списка с индексом \"" + itemIndex + "\" имеет значение \""
                + integersList1.get(itemIndex) + "\"");
        System.out.println();

        int setIndex = 0;
        Integer setValue = 1000;
        System.out.println("Установим в Списке №1 по индексу \"" + setIndex + "\" значение \"" + setValue + "\"");
        System.out.println("Прежнее значение по этому индексу было \"" + integersList1.set(setIndex, setValue) + "\"");
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        int removeItemIndex = 0;
        System.out.println("Удалим из Списка №1 элемент по индексу \"" + removeItemIndex + "\", который имел значение "
                + "\"" + integersList1.remove(removeItemIndex) + "\".");
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        Integer searchedItem = null;
        int firstIndexOfItem = integersList1.indexOf(searchedItem);
        System.out.println("Первое вхождение в Список №1 элемента со значением \"" + searchedItem + "\" "
                + (firstIndexOfItem == -1 ? "определить невозможно, т.к. такого элемента в списке нет"
                : "по индексу \"" + firstIndexOfItem + "\""));

        int lastIndexOfItem = integersList1.lastIndexOf(searchedItem);
        System.out.println("Последнее вхождение в Список №1 элемента со значением \"" + searchedItem + "\" "
                + (lastIndexOfItem == -1 ? "определить невозможно, т.к. такого элемента в списке нет"
                : "по индексу \"" + lastIndexOfItem + "\"."));

        System.out.println();

        System.out.println("Создадим Список №2:");
        ArrayList<Integer> integersList2 = new ArrayList<>();

        for (int i = 15; i <= 17; ++i) {
            integersList2.add(i);
        }

        integersList2.add(15);
        integersList2.add(7);

        System.out.println(integersList2);
        System.out.println();

        System.out.println("Список №1" + (integersList1.containsAll(integersList2) ? " " : " НЕ ") + "содержит в себе все "
                + "элементы Списка №2");
        System.out.println();

        System.out.println("Добавим в конец Списка №1 Список №2");
        integersList1.addAll(integersList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        int addAllIndex = 2;
        System.out.println("Вставим Список №2 в Список №1 по индексу \"" + addAllIndex + "\"");
        integersList1.addAll(addAllIndex, integersList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        System.out.println("Создадим Список №3, который является точной копией Списка №1:");
        ArrayList<Integer> integersList3 = new ArrayList<>(integersList1.size());
        integersList3.addAll(integersList1);
        System.out.println(integersList3);
        System.out.println();

        System.out.println("Удалим из Списка №1 все элементы Списка №2");
        integersList1.removeAll(integersList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integersList1);
        System.out.println();

        System.out.println("Список №1 " + (integersList1.equals(integersList3) ? "" : "не ") + "равен Списку №3");
        System.out.println();

        System.out.println("Удалим из Списка №3 все элементы, кроме тех, которые есть в Списке №1");
        integersList3.retainAll(integersList1);
        System.out.println("Список №3 принял вид:");
        System.out.println(integersList3);
        System.out.println();

        System.out.println("Удалим из Списка №3 все элементы");
        integersList3.clear();
        System.out.println("Список №3 принял вид:");
        System.out.println(integersList3);
        System.out.println();

        System.out.println("Хэш-код Списка №1: " + integersList1.hashCode());
        System.out.println("Хэш-код Списка №2: " + integersList2.hashCode());
        System.out.println("Хэш-код Списка №3: " + integersList3.hashCode());
    }
}
