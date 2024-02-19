package ru.academits.valyukhov.arraylist_main;

import ru.academits.valyukhov.arraylist.ArrayList;

public class ArrayListMain {
    public static void main(String[] args) {
        ArrayList <Integer> integerArrayList1 = new ArrayList<>();
        integerArrayList1.add(1);
        integerArrayList1.add(2);
        integerArrayList1.add(3);
        integerArrayList1.add(4);
        integerArrayList1.add(5);
        System.out.println("Список №1:");
        System.out.println(integerArrayList1);

        ArrayList <Integer> integerArrayList2 = new ArrayList<>(integerArrayList1);
        System.out.println("Список №2 (копия Списка №1):");
        System.out.println(integerArrayList2);

        ArrayList <Integer> integerArrayList3 = new ArrayList<>(10);
        System.out.println("Список №3:");
        System.out.println(integerArrayList3);

    }
}
