package ru.academits.valyukhov.list_main;

import ru.academits.valyukhov.list.Node;
import ru.academits.valyukhov.list.SinglyLinkedList;

public class ListMain {
    public static void print(SinglyLinkedList list) {
        if (list.head == null) {
            System.out.println("Список пуст.");
            return;
        }

        Node<Integer> currentNode = list.head;

        System.out.print("{");

        while (currentNode.hasNext()) {
            System.out.print(currentNode.getData() + ", ");
            currentNode = currentNode.getNext();
        }

        System.out.print(currentNode.getData());
        System.out.println("}");
    }

    public static void main(String[] args) throws NullPointerException {
        SinglyLinkedList list = new SinglyLinkedList();

        for (int i = 10; i >= 1; --i) {
            Node<Integer> node = new Node<>(i);
            list.insertFirstNode(node);
        }

        System.out.println("Создан список целых чисел:");
        print(list);
        System.out.println("Размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Первый элемент списка имеет значение: " + list.getFistNodeData());
        System.out.println("Второй элемент списка имеет значение: " + list.getDataByIndex(1));
        System.out.println("Седьмой элемент списка имеет значение: " + list.getDataByIndex(6));
        System.out.println("Последний элемент списка имеет значение: " + list.getDataByIndex(list.getSize() - 1));
        System.out.println();

        int replacementData = 87;
        System.out.printf("Заменим текущее значение второго элемента списка \"%d\" на \"%d\".%n",
                list.setDataByIndex(1, replacementData), replacementData);
        System.out.println("Теперь второй элемент списка имеет значение: " + list.getDataByIndex(1));
        System.out.println("Список принял вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        int deleteNodeIndex = 0;
        System.out.printf("Удалим из списка элемент с индексом \"%d\", который содержал значение \"%d\".%n", deleteNodeIndex,
                list.deleteNodeByIndex(deleteNodeIndex));
        System.out.println("Список принял вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        Node<Integer> newNode1 = new Node<>(100);
        int insertIndex = 7;
        System.out.printf("Вставим в список узел со значением \"%d\" по индексу \"%d\".%n", newNode1.getData(), insertIndex);
        list.insertNodeByIndex(insertIndex, newNode1);
        System.out.println("Список принял вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        int deleteNodeData = 7;
        System.out.printf("Попытаемся удалить узел со значением \"%d\".%n", deleteNodeData);
        System.out.printf(list.deleteNodeByData(deleteNodeData) ? "Узел со значением \"%d\" успешно удалён.%n"
                : "Узел со значением \"%d\" в списке не найден.%n", deleteNodeData, deleteNodeData);
        System.out.println("Список имеет вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Удалим из списка первый узел, который имел значение: " + list.deleteFirstNode());
        System.out.println("Список принял вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Развернём список в обратном порядке.");
        list.reverseList();
        System.out.println("Список принял вид:");
        print(list);
        System.out.println("Текущий размер списка: " + list.getSize());
        System.out.println();

        System.out.println("Создадим дубликат списка: ");
        SinglyLinkedList listDuplicate = list.getListDuplicate();
        print(listDuplicate);
        System.out.println("Размер дубликата: " + listDuplicate.getSize());
        System.out.println();

        System.out.println("Сверим текущий размер списка:");
        System.out.println(" - полученный из переменной \"size\":        " + list.getSize());
        System.out.println(" - полученный путём перебора узлов списка: " + list.getSizeByIteratingNodes());
    }
}
