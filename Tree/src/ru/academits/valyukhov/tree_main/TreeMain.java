package ru.academits.valyukhov.tree_main;

import ru.academits.valyukhov.tree.Tree;
import ru.academits.valyukhov.tree.TreeNode;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TreeMain {
    public static void main(String[] args) {
        Tree<Integer> testTree = new Tree<>(17,
                new TreeNode<>(5,
                        new TreeNode<>(3,
                                new TreeNode<>(1,
                                        null,
                                        null),
                                new TreeNode<>(4,
                                        null,
                                        null)),
                        new TreeNode<>(7,
                                null,
                                new TreeNode<>(12,
                                        new TreeNode<>(9,
                                                null,
                                                null),
                                        null))),
                new TreeNode<>(20,
                        new TreeNode<>(19,
                                null,
                                null),
                        new TreeNode<>(28,
                                null,
                                null)));

        System.out.println();
        System.out.println("Дерево \"testTree\":");
        testTree.print();

        Tree<Integer> tree = new Tree<>(Integer::compareTo);

        Integer[] valueArray = new Integer[]{17, 5, 20, 3, 7, 19, 28, 1, 4, 12, 9};

        IntStream intStream = Arrays.stream(valueArray).mapToInt(Integer::valueOf);

        intStream.forEach(tree::addNode);    // Вставка новых узлов по значениям

        System.out.println();
        System.out.println("Дерево \"tree\":");
        tree.print();

        // Поиск узла по значению
        Integer searchedValue = 1;

        System.out.println();
        System.out.printf("Осуществим поиск узла со значением \"%d\" в дереве \"tree\".%n", searchedValue);
        System.out.print("Результат: ");

        TreeNode<Integer> nodeObtainedByValue = tree.getNodeByValue(searchedValue);

        if (nodeObtainedByValue == null) {
            System.out.println("узла со значением \"" + searchedValue + "\" в дереве \"tree\" нет.");
        } else {
            System.out.println("узел со значением \"" + searchedValue + "\" в дереве \"tree\" имеется.");
            System.out.println("Проверка: значение найденного узла: " + nodeObtainedByValue.getValue());
        }

        // Удаление первого вхождения узла по значению
        Integer deletedNodeValue = 17;

        System.out.println();
        System.out.println("Попытаемся удалить из дерева \"tree\" первое вхождение узла со значением \""
                + deletedNodeValue + "\"");
        System.out.print("Результат: ");

        if (!tree.deleteNode(deletedNodeValue)) {
            System.out.println("узел с таким значением не удалён, т.к. в этом дереве его не существует.");
        } else {
            System.out.println("искомый узел удалён.");
            System.out.println("Дерево \"tree\" приняло вид:");
            tree.print();
        }

        // Получение числа элементов
        System.out.println();
        System.out.println("Количество узлов в дереве \"tree\": " + tree.getSize());

        Consumer<TreeNode<Integer>> printConsumer = node -> System.out.print(node.getValue() + ", ");

        // Обход дерева в глубину с рекурсией
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину с рекурсией:");

        tree.recursiveDepthFirstTreeTraversal(tree.getRootNode(), printConsumer);

        System.out.println("\b" + "\b");

        // Обход дерева в глубину без рекурсии
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в глубину без рекурсии:");

        tree.depthFirstTreeTraversal(tree.getRootNode(), printConsumer);

        System.out.println("\b" + "\b");

        // Обход дерева в ширину
        System.out.println();
        System.out.println("Значения узлов в порядке обхода дерева \"tree\" в ширину:");

        tree.breadthFirstTreeTraversal(tree.getRootNode(), printConsumer);

        System.out.println("\b" + "\b");
    }
}
