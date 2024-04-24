package ru.academits.valyukhov.hashtable_main;

import ru.academits.valyukhov.hashtable.HashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class HashTableMain {
    public static void main(String[] args) {
        int itemsCount = 12;
        System.out.println("Создадим объекты типа Integer со значениями от \"0\" до \"" + (itemsCount - 1) + "\" и поместим "
                + "их в Хэш-таблицу №1:");
        HashTable<Integer> hashTable1 = new HashTable<>();

        for (int i = 0; i < itemsCount; ++i) {
            hashTable1.add(i);
        }

        System.out.println(hashTable1);
        System.out.println("Хэш-таблица №1 " + (hashTable1.isEmpty() ? "пустая." : "не пустая."));
        System.out.println("Количество элементов в Хэш-таблице №1: " + hashTable1.size());
        System.out.println();

        Integer itemForRemove = 6;
        System.out.println("Попытаемся удалить из Хэш-таблицы №1 элемент со значением \"" + itemForRemove + "\".");

        if (!hashTable1.contains(itemForRemove)) {
            System.out.println("Удаление невозможно, т.к. такой элемент в Хэш-таблице №1 отсутствует.");
        } else {
            System.out.println("Такой элемент в Хэш-таблице №1 имеется.");

            if (hashTable1.remove(itemForRemove)) {
                System.out.println("Элемент со значением \"" + itemForRemove + "\" успешно удалён, а Хэш-таблица №1 приняла вид:");
                System.out.println(hashTable1);
                System.out.println("Количество элементов в Хэш-таблице №1 теперь: " + hashTable1.size());
            }
        }

        System.out.println();
        System.out.println("Создадим массив элементов аналогичный Хэш-таблице №1:");
        Object[] array = hashTable1.toArray();
        System.out.println(Arrays.toString(array));
        System.out.println();

        System.out.println("Создадим массив целых чисел:");
        Integer[] integersArray = new Integer[15];

        for (int i = 15, j = 0; i > 0; i--, j++) {
            integersArray[j] = i;
        }

        System.out.println(Arrays.toString(integersArray));
        System.out.println("А теперь вставим в него значения Хэш-таблицы №1, воспользовавшись методом \"public <T> T[] toArray(T[] a)\".");
        integersArray = hashTable1.toArray(integersArray);
        System.out.println("Массив принял вид:");
        System.out.println(Arrays.toString(integersArray));
        System.out.println();

        System.out.println("Создадим Список целых чисел:");
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(3);
        integerList.add(4);
        integerList.add(36);
        integerList.add(6);
        integerList.add(42);
        System.out.println(integerList);
        System.out.println("Хэш-таблица №1" + (hashTable1.containsAll(integerList) ? " " : " НЕ ") + "содержит в себе все "
                + "элементы указанного Списка.");

        System.out.println();
        System.out.println("Поместим элементы этого Списка в Хэш-таблицу №1.");

        if (hashTable1.addAll(integerList)) {
            System.out.println("Хэш-таблица №1 приняла вид:");
            System.out.println(hashTable1);
        } else {
            System.out.println("Список оказался пустым. Хэш-таблица №1 не изменилась:");
            System.out.println(hashTable1);
        }

        System.out.println();
        System.out.println("Теперь удалим из Хэш-таблицы №1 элементы равные элементам Списка целых чисел.");

        if (hashTable1.removeAll(integerList)) {
            System.out.println("Хэш-таблица №1  приняла вид:");
            System.out.println(hashTable1);
        } else {
            System.out.println("Список оказался пустым. Хэш-таблица №1 не изменилась:");
            System.out.println(hashTable1);
        }

        System.out.println();

        int hashCodesCount = 7;
        int value1 = 1;
        int value2 = 2;
        int value3 = 3;

        System.out.println("Создадим Хэш-таблицу №2 размерностью: " + hashCodesCount + "\" и поместим в неё элементы со "
                + "значениями " + value1 + ", " + value2 + " и " + value3);
        HashTable<Integer> hashTable2 = new HashTable<>(hashCodesCount);

        hashTable2.add(value1);
        hashTable2.add(value2);
        hashTable2.add(value3);

        System.out.println(hashTable2);

        System.out.println();
        System.out.println("Количество элементов в Хэш-таблице №2: " + hashTable2.size());

        System.out.println();
        System.out.println("Теперь оставим в Хэш-таблице №1 только элементы равные элементам Хэш-таблицы №2.");

        if (hashTable1.retainAll(hashTable2)) {
            System.out.println("Хэш-таблица №1  приняла вид:");
            System.out.println(hashTable1);
        } else {
            System.out.println("Хэш-таблица №2 пустая, поэтому Хэш-таблица №1 не изменилась:");
            System.out.println(hashTable1);
        }

        System.out.println();
        System.out.println("Удалим из Хэш-таблицы №1 все элементы.");
        hashTable1.clear();
        System.out.println("Хэш-таблица №1 приняла вид:");
        System.out.println(hashTable1);

        System.out.println();
        System.out.println("Хэш-код Хэш-таблицы №1: " + hashTable1.hashCode());
        System.out.println("Хэш-код Хэш-таблицы №2: " + hashTable2.hashCode());
    }
}
