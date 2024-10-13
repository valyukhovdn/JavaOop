package ru.academits.valyukhov.tree_main;

import ru.academits.valyukhov.tree.BinarySearchTree;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TreeMain {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        Integer[] nodeValuesArray = {17, 5, 20, 3, 7, 19, 28, 1, 4, 12, 9, 6, 10};

        IntStream intStream = Arrays.stream(nodeValuesArray).mapToInt(Integer::valueOf);

        intStream.forEach(tree::addNodeWithValue);    // Вставка новых узлов по значениям

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

        if (tree.isNodeWithValue(searchedValue)) {
            System.out.println("узел со значением \"" + searchedValue + "\" в дереве \"tree\" имеется.");
        } else {
            System.out.println("узла со значением \"" + searchedValue + "\" в дереве \"tree\" нет.");
        }

        // Удаление первого вхождения узла по значению
        Integer deletedNodeValue = 7;

        System.out.println();
        System.out.println("Попытаемся удалить из дерева \"tree\" первое вхождение узла со значением \""
                + deletedNodeValue + "\"");
        System.out.print("Результат: ");

        if (!tree.deleteNode(deletedNodeValue)) {
            System.out.println("узел со значением \"" + deletedNodeValue + "\" не удалён, т.к. в этом дереве такого узла нет.");
        } else {
            System.out.println("узел со значением \"" + deletedNodeValue + "\" удалён.");
            System.out.println("Дерево \"tree\" приняло вид:");
            System.out.println(tree);
        }

        // Получение числа элементов
        System.out.println();
        System.out.println("Количество узлов в дереве \"tree\": " + tree.getSize());

        // Обход дерева в глубину с рекурсией
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину с рекурсией:");

        Consumer<Integer> printConsumer = value -> System.out.print(value + ", ");

        tree.recursiveDepthFirstTraversal(printConsumer);

        System.out.println("\b\b");

        // Обход дерева в глубину без рекурсии
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину без рекурсии:");

        tree.depthFirstTraversal(printConsumer);

        System.out.println("\b\b");

        // Обход дерева в ширину
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в ширину:");

        tree.breadthFirstTraversal(printConsumer);

        System.out.println("\b\b");
    }
}