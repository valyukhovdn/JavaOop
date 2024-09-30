package ru.academits.valyukhov.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<E> {
    private TreeNode<E> rootNode;
    private Comparator<E> comparator;

    public Tree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public Tree(E value) {
        rootNode = new TreeNode<>(value);
    }

    public Tree(E value, Comparator<E> comparator) {
        rootNode = new TreeNode<>(value);
        this.comparator = comparator;
    }

    public Tree(E value, TreeNode<E> left, TreeNode<E> right) {
        rootNode = new TreeNode<>(value, left, right);
    }

    public Tree(E value, TreeNode<E> left, TreeNode<E> right, Comparator<E> comparator) {
        rootNode = new TreeNode<>(value, left, right);
        this.comparator = comparator;
    }

    public TreeNode<E> getRootNode() {
        return rootNode;
    }

    public void print() {
        String line = "-------------------------------------------------------------------------------------------------";
        System.out.println(line);

        int gaps = 50;
        boolean isLastRawEmpty = false;

        Deque<TreeNode<E>> outerStack = new LinkedList<>();
        outerStack.offerFirst(rootNode);

        while (!isLastRawEmpty) {
            isLastRawEmpty = true;

            for (int i = 0; i < gaps; i++) {
                System.out.print(' ');
            }

            Deque<TreeNode<E>> innerStack = new LinkedList<>();

            while (!outerStack.isEmpty()) {
                TreeNode<E> tempNode = outerStack.pollFirst();

                if (tempNode != null) {
                    System.out.printf("%4s", tempNode.getValue());
                    innerStack.offerFirst(tempNode.getLeft());
                    innerStack.offerFirst(tempNode.getRight());

                    if (tempNode.hasLeft() || tempNode.hasRight()) {
                        isLastRawEmpty = false;
                    }
                } else {
                    System.out.print(" -- ");
                    innerStack.offerFirst(null);
                    innerStack.offerFirst(null);
                }

                for (int i = 0; i < gaps * 2 - 4; i++) {
                    System.out.print(' ');
                }
            }

            System.out.println();

            gaps /= 2;

            while (!innerStack.isEmpty()) {
                outerStack.offerFirst(innerStack.pollFirst());
            }
        }

        System.out.println(line);
    }

    // Вставка нового узла по значению
    public void addNode(E value) {
        if (rootNode == null) {
            rootNode = new TreeNode<>(value);
            return;
        }

        TreeNode<E> currentNode = rootNode;

        while (true) {
            if (comparator.compare(value, currentNode.getValue()) < 0) {
                if (currentNode.hasLeft()) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new TreeNode<>(value));
                    return;
                }
            } else {
                if (currentNode.hasRight()) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new TreeNode<>(value));
                    return;
                }
            }
        }
    }

    // Поиск узла по значению
    public TreeNode<E> getNodeByValue(E value) {
        if (value == null) {
            return null;
        }

        TreeNode<E> currentNode = rootNode;

        while (true) {
            if (comparator.compare(value, currentNode.getValue()) == 0) {
                return currentNode;
            }

            if (comparator.compare(value, currentNode.getValue()) < 0) {
                if (currentNode.getLeft() == null) {
                    return null;
                }

                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    return null;
                }

                currentNode = currentNode.getRight();
            }
        }
    }

    // Нахождение родителя удаляемого по значению узла.
    private TreeNode<E> getNodeByValueParent(E value) {
        if (value == null || comparator.compare(value, rootNode.getValue()) == 0) {
            return null;
        }

        TreeNode<E> parentNode = rootNode;
        TreeNode<E> currentNode = rootNode;

        while (true) {
            if (comparator.compare(value, currentNode.getValue()) == 0) {
                return parentNode;
            }

            if (comparator.compare(value, currentNode.getValue()) < 0) {
                if (currentNode.getLeft() == null) {
                    return null;
                }

                parentNode = currentNode;
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    return null;
                }
                parentNode = currentNode;
                currentNode = currentNode.getRight();
            }
        }
    }

    // Удаление первого вхождения узла по значению
    public boolean deleteNode(E value) {
        TreeNode<E> deletedNode = getNodeByValue(value);

        // Если нет узла для удаления
        if (deletedNode == null) {
            return false;
        }

        // Если у удаляемого узла два ребёнка
        TreeNode<E> deletedNodeParent = getNodeByValueParent(value);

        if (deletedNode.hasLeft() && deletedNode.hasRight()) {
            TreeNode<E> nodeForReplace = deletedNode.getRight();

            while (nodeForReplace.hasLeft()) {
                nodeForReplace = nodeForReplace.getLeft();
            }

            TreeNode<E> nodeForReplaceParent = getNodeByValueParent(nodeForReplace.getValue());

            if (nodeForReplace.hasRight()) {
                if (comparator.compare(nodeForReplace.getValue(), nodeForReplaceParent.getValue()) < 0) {
                    nodeForReplaceParent.setLeft(nodeForReplace.getRight());
                } else {
                    nodeForReplaceParent.setRight(nodeForReplace.getRight());
                }
            } else {
                if (comparator.compare(nodeForReplace.getValue(), nodeForReplaceParent.getValue()) < 0) {
                    nodeForReplaceParent.setLeft(null);
                } else {
                    nodeForReplaceParent.setRight(null);
                }
            }

            nodeForReplace.setLeft(deletedNode.getLeft());
            nodeForReplace.setRight(deletedNode.getRight());

            if (deletedNode == rootNode) {      // В случае удаления корня
                rootNode = nodeForReplace;
            } else if (comparator.compare(value, deletedNodeParent.getValue()) < 0) {
                deletedNodeParent.setLeft(nodeForReplace);
            } else {
                deletedNodeParent.setRight(nodeForReplace);
            }

            return true;
        }

        // Если у удаляемого узла нет детей
        if (!deletedNode.hasLeft() && !deletedNode.hasRight()) {
            if (deletedNode == rootNode) {      // В случае удаления корня
                rootNode = null;
            } else if (comparator.compare(value, deletedNodeParent.getValue()) < 0) {
                deletedNodeParent.setLeft(null);
            } else {
                deletedNodeParent.setRight(null);
            }

            return true;
        }

        // Если у удаляемого узла есть только левый сын
        if (deletedNode.hasLeft() && !deletedNode.hasRight()) {
            if (deletedNode == rootNode) {      // В случае удаления корня
                rootNode = rootNode.getLeft();
            } else if (comparator.compare(value, deletedNodeParent.getValue()) < 0) {
                deletedNodeParent.setLeft(deletedNode.getLeft());
            } else {
                deletedNodeParent.setRight(deletedNode.getLeft());
            }

            return true;
        }

        // Если у удаляемого узла есть только правый сын
        if (!deletedNode.hasLeft() && deletedNode.hasRight()) {
            if (deletedNode == rootNode) {      // В случае удаления корня
                rootNode = rootNode.getRight();
            } else if (comparator.compare(value, deletedNodeParent.getValue()) < 0) {
                deletedNodeParent.setLeft(deletedNode.getRight());
            } else {
                deletedNodeParent.setRight(deletedNode.getRight());
            }

            return true;
        }

        return false;
    }

    // Получение числа элементов
    public int getSize() {
        if (rootNode == null) {
            return 0;
        }

        LinkedList<TreeNode<E>> nodeLinkedList = new LinkedList<>();

        Queue<TreeNode<E>> queue = new LinkedList<>();

        TreeNode<E> currentNode = rootNode;

        queue.offer(currentNode);

        while (!queue.isEmpty()) {
            currentNode = queue.poll();

            nodeLinkedList.add(currentNode);

            if (currentNode.hasLeft()) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.hasRight()) {
                queue.offer(currentNode.getRight());
            }
        }

        return nodeLinkedList.size();
    }

    // Обход в глубину с рекурсией
    public void recursiveDepthFirstTreeTraversal(TreeNode<E> node, Consumer<TreeNode<E>> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node);

        recursiveDepthFirstTreeTraversal(node.getLeft(), consumer);
        recursiveDepthFirstTreeTraversal(node.getRight(), consumer);
    }

    // Обход в глубину без рекурсии
    public void depthFirstTreeTraversal(TreeNode<E> node, Consumer<TreeNode<E>> consumer) {
        Deque<TreeNode<E>> stack = new LinkedList<>();

        stack.offerFirst(node);

        while (!stack.isEmpty()) {
            TreeNode<E> currentNode = stack.pollFirst();
            consumer.accept(currentNode);

            if (currentNode.hasRight()) {
                stack.offerFirst(currentNode.getRight());
            }

            if (currentNode.hasLeft()) {
                stack.offerFirst(currentNode.getLeft());
            }
        }
    }

    // Обход дерева в ширину
    public void breadthFirstTreeTraversal(TreeNode<E> node, Consumer<TreeNode<E>> consumer) {
        Queue<TreeNode<E>> queue = new LinkedList<>();

        queue.offer(node);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.poll();

            consumer.accept(currentNode);

            if (currentNode.hasLeft()) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.hasRight()) {
                queue.offer(currentNode.getRight());
            }
        }
    }
}