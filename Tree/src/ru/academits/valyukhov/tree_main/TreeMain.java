package ru.academits.valyukhov.tree_main;

import ru.academits.valyukhov.tree.BinarySearchTree;

import java.util.Arrays;
import java.util.function.Consumer;

public class TreeMain {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        Integer[] nodeValuesArray = {17, 5, 20, 3, 7, 19, 28, 1, 4, 12, 9, 6, 10, 15, 13, null, null};

        Arrays.stream(nodeValuesArray).forEach(tree::add);    // Вставка новых узлов по значениям

        System.out.println();
        System.out.println("Дерево \"tree\":");
        System.out.println(tree);

        // Получение числа элементов
        System.out.println();
        System.out.println("Количество узлов в дереве \"tree\": " + tree.getSize());

        // Поиск узла по значению
        Integer searchedValue = 1;

        System.out.println();
        System.out.printf("Осуществим поиск узла со значением \"%d\" в дереве \"tree\".%n", searchedValue);
        System.out.print("Результат: ");

        if (tree.containsNodeWithValue(searchedValue)) {
            System.out.println("узел со значением \"" + searchedValue + "\" в дереве \"tree\" имеется.");
        } else {
            System.out.println("узла со значением \"" + searchedValue + "\" в дереве \"tree\" нет.");
        }

        // Удаление первого вхождения узла по значению
        Integer removedNodeValue = 5;

        System.out.println();
        System.out.println("Попытаемся удалить из дерева \"tree\" первое вхождение узла со значением \""
                + removedNodeValue + "\"");
        System.out.print("Результат: ");

        if (!tree.remove(removedNodeValue)) {
            System.out.println("узел со значением \"" + removedNodeValue + "\" не удалён, т.к. в этом дереве такого узла нет.");
        } else {
            System.out.println("узел со значением \"" + removedNodeValue + "\" удалён.");
            System.out.println("Дерево \"tree\" приняло вид:");
            System.out.println(tree);
        }

        // Получение числа элементов
        System.out.println();
        System.out.println("Количество узлов в дереве \"tree\": " + tree.getSize());

        Consumer<Integer> printConsumer = value -> System.out.print(value + ", ");

        // Обход дерева в ширину
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в ширину:");

        tree.breadthFirstTraversal(printConsumer);

        System.out.println("\b\b");

        // Обход дерева в глубину без рекурсии
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину без рекурсии:");

        tree.depthFirstTraversal(printConsumer);

        System.out.println("\b\b");

        // Обход дерева в глубину с рекурсией
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину с рекурсией:");

        tree.depthFirstTraversalWithRecursion(printConsumer);

        System.out.println("\b\b");
    }
}