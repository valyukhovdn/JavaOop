package ru.academits.valyukhov.arraylist_main;

import ru.academits.valyukhov.arraylist.ArrayList;

import java.util.Arrays;

public class ArrayListMain {
    public static void main(String[] args) {
        System.out.println("Создадим Список №1:");
        ArrayList<Integer> integerList1 = new ArrayList<>(10);

        for (int i = 1; i <= 8; ++i) {
            integerList1.add(i);
        }

        System.out.println(integerList1);
        System.out.println("Список №1 " + (integerList1.isEmpty() ? "пуст" : "НЕ пуст"));
        System.out.println("Количество элементов в Списке №1: " + integerList1.size());
        System.out.println();

        System.out.println("Создадим массив элементов аналогичный Списку №1:");
        System.out.println(Arrays.toString(integerList1.toArray()));
        System.out.println();

        System.out.println("Содержимое внутреннего массива Списка №1:");
        System.out.println(integerList1.toStringFullCapacity());
        System.out.println();

        int ensuredCapacity = 15;
        System.out.printf("Сделаем, чтобы размер внутреннего массива Списка №1 был не менее \"%d\".%n", ensuredCapacity);
        integerList1.ensureCapacity(ensuredCapacity);
        System.out.println("Содержимое внутреннего массива Списка №1 стало:");
        System.out.println(integerList1.toStringFullCapacity());
        System.out.println();

        System.out.println("Сократим внутренний массив Списка №1 до размеров этого списка.");
        integerList1.trimToSize();
        System.out.println("Содержимое внутреннего массива Списка №1 стало:");
        System.out.println(integerList1.toStringFullCapacity());
        System.out.println();

        Integer elementForDelete = 4;
        System.out.println("Попытаемся удалить из списка элемент со значением \"" + elementForDelete + "\".");

        if (integerList1.contains(elementForDelete)) {
            System.out.println("Такой элемент в Списке №1 содержится.");

            if (integerList1.remove(elementForDelete)) {
                System.out.println("Элемент со значением \"" + elementForDelete + "\" успешно удалён, а Список №1 принял вид:");
                System.out.println(integerList1);
            }
        } else {
            System.out.println("Удаление не возможно, т.к. такой элемент в Списке №1 отсутствует.");
        }

        System.out.println();

        Integer insertElement = 7;
        int insertIndex = 7;
        System.out.println("Вставим в список элемент со значением \"" + insertElement + "\" по индексу \"" + insertIndex + "\".");
        integerList1.add(insertIndex, insertElement);
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        int elementIndex = 5;
        System.out.println("Элемент списка с индексом \"" + elementIndex + "\" имеет значение \""
                + integerList1.get(elementIndex) + "\"");
        System.out.println();

        int setIndex = 0;
        Integer setValue = 1000;
        System.out.println("Установим в Списке №1 по индексу \"" + setIndex + "\" значение \"" + setValue + "\".");
        System.out.println("Прежнее значение по этому индексу было \"" + integerList1.set(setIndex, setValue) + "\".");
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        int removeElementIndex = 0;
        System.out.println("Удалим из Списка №1 элемент по индексу \"" + removeElementIndex + "\", который имел значение "
                + "\"" + integerList1.remove(removeElementIndex) + "\".");
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        Integer searchedElement = 7;
        int firstIndexOfElement = integerList1.indexOf(searchedElement);
        System.out.println("Первое вхождение в Список №1 элемента со значением \"" + searchedElement + "\" "
                + (firstIndexOfElement == -1 ? "определить невозможно, т.к. такого элемента в списке нет."
                : "по индексу\"" + firstIndexOfElement + "\"."));

        int lastIndexOfElement = integerList1.lastIndexOf(searchedElement);
        System.out.println("Последнее вхождение в Список №1 элемента со значением \"" + searchedElement + "\" "
                + (lastIndexOfElement == -1 ? "определить невозможно, т.к. такого элемента в списке нет."
                : "по индексу\"" + lastIndexOfElement + "\"."));

        System.out.println();

        System.out.println("Создадим Список №2:");
        ArrayList<Integer> integerList2 = new ArrayList<>();

        for (int i = 5; i <= 7; ++i) {
            integerList2.add(i);
        }

        integerList2.add(5);

        System.out.println(integerList2);
        System.out.println();

        System.out.println("Список №1" + (integerList1.containsAll(integerList2) ? " " : " НЕ ") + "содержит в себе все "
                + "элементы Списка №2.");
        System.out.println();

        System.out.println("Добавим в конец Списка №1 Список №2");
        integerList1.addAll(integerList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        int addAllIndex = 2;
        System.out.println("Вставим Список №2 в Список №1 по индексу \"" + addAllIndex + "\"");
        integerList1.addAll(addAllIndex, integerList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        System.out.println("Создадим Список №3, который является точной копией Списка №1:");
        ArrayList<Integer> integerList3 = new ArrayList<>(integerList1.size());
        integerList3.addAll(integerList1);
        System.out.println(integerList3);
        System.out.println();

        System.out.println("Удалим из Списка №1 все элементы Списка №2");
        integerList1.removeAll(integerList2);
        System.out.println("Список №1 принял вид:");
        System.out.println(integerList1);
        System.out.println();

        System.out.println("Создадим Список №4:");
        ArrayList<Integer> integerList4 = new ArrayList<>(10);
        integerList4.add(3);
        integerList4.add(4);
        integerList4.add(5);
        integerList4.add(4);
        System.out.println(integerList4);
        System.out.println();

        System.out.println("Удалим из Списка №3 все элементы, кроме тех, которые есть в Списке №4");
        integerList3.retainAll(integerList4);
        System.out.println("Список №3 принял вид:");
        System.out.println(integerList3);
        System.out.println();

        System.out.println("Удалим из Списка №4 все элементы.");
        integerList4.clear();
        System.out.println("Список №4 принял вид:");
        System.out.println(integerList4);
    }
}
