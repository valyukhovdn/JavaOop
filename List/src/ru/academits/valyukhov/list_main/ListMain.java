package ru.academits.valyukhov.list_main;

import ru.academits.valyukhov.list.SinglyLinkedList;

public class ListMain {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        for (int i = 10; i >= 1; --i) {
            list.insertFirst(i);
        }

        System.out.println("Создан список целых чисел:");
        System.out.println(list);
        System.out.println("Размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Первый элемент списка имеет значение: " + list.getFirst());
        System.out.println("Второй элемент списка имеет значение: " + list.get(1));
        System.out.println("Седьмой элемент списка имеет значение: " + list.get(6));
        System.out.println("Последний элемент списка имеет значение: " + list.get(list.getSize() - 1));
        System.out.println();

        int replacementData = 87;
        System.out.printf("Заменим текущее значение второго элемента списка \"%d\" на \"%d\".%n",
                list.set(1, replacementData), replacementData);
        System.out.println("Теперь второй элемент списка имеет значение: " + list.get(1));
        System.out.println("Список принял вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        int deleteNodeIndex = 0;
        System.out.printf("Удалим из списка элемент с индексом \"%d\", который содержал значение \"%d\".%n", deleteNodeIndex,
                list.deleteElementByIndex(deleteNodeIndex));
        System.out.println("Список принял вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        int insertIndex = 7;
        Integer insertData = 100;
        System.out.printf("Вставим в список элемент со значением \"%d\" по индексу \"%d\".%n", insertData, insertIndex);
        list.insertElement(insertIndex, insertData);
        System.out.println("Список принял вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        int deleteElement = 7;
        System.out.printf("Попытаемся удалить из списка элемент со значением \"%d\".%n", deleteElement);
        System.out.printf(list.deleteElement(deleteElement) ? "Элемент со значением \"%d\" успешно удалён.%n"
                : "Элемент со значением \"%d\" в списке не найден.%n", deleteElement, deleteElement);
        System.out.println("Список имеет вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Удалим из списка первый элемент, который имел значение: " + list.deleteFirst());
        System.out.println("Список принял вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Развернём список в обратном порядке.");
        list.reverse();
        System.out.println("Список принял вид:");
        System.out.println(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Создадим дубликат списка:");
        SinglyLinkedList<Integer> listDuplicate = list.getListCopy();
        System.out.println(listDuplicate);
        System.out.println("Размер дубликата: " + listDuplicate.getSize());
        System.out.println();
    }
}
