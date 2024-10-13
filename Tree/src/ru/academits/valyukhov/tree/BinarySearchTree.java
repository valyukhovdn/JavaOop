package ru.academits.valyukhov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    private TreeNode<E> rootNode;
    private final Comparator<E> comparator;
    private int size;

    public BinarySearchTree() {
        comparator = (Comparator<E>) Comparator.naturalOrder();
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    // Получение числа элементов
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        String line = "-------------------------------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------------------------------";
        stringBuilder.append(line).append("\n");

        int gaps = 100;
        boolean isRawEmpty = false;

        Deque<TreeNode<E>> outerStack = new LinkedList<>();
        outerStack.push(rootNode);

        while (!isRawEmpty) {
            isRawEmpty = true;

            stringBuilder.append(" ".repeat(gaps));

            int innerGaps = gaps * 2 - 4;

            Deque<TreeNode<E>> innerStack = new LinkedList<>();

            while (!outerStack.isEmpty()) {
                TreeNode<E> currentNode = outerStack.pollFirst();

                if (currentNode != null) {
                    stringBuilder.append(String.format("%4s", currentNode.getValue()));
                    innerStack.push(currentNode.getLeft());
                    innerStack.push(currentNode.getRight());

                    if (currentNode.hasLeft() || currentNode.hasRight()) {
                        isRawEmpty = false;
                    }
                } else {
                    stringBuilder.append(" -- ");
                    innerStack.push(null);
                    innerStack.push(null);
                }

                stringBuilder.append(" ".repeat(innerGaps));
            }

            stringBuilder.append("\n");

            gaps /= 2;

            while (!innerStack.isEmpty()) {
                outerStack.offerFirst(innerStack.pollFirst());
            }
        }

        stringBuilder.append(line);

        return stringBuilder.toString();
    }

    // Вставка нового узла по значению
    public void addNodeWithValue(E value) {
        if (value == null) {
            return;
        }

        if (rootNode == null) {
            rootNode = new TreeNode<>(value);
            ++size;
            return;
        }

        TreeNode<E> currentNode = rootNode;

        while (true) {
            if (comparator.compare(value, currentNode.getValue()) < 0) {
                if (currentNode.hasLeft()) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new TreeNode<>(value));
                    ++size;
                    return;
                }
            } else {
                if (currentNode.hasRight()) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new TreeNode<>(value));
                    ++size;
                    return;
                }
            }
        }
    }

    // Поиск узла по значению
    public boolean isNodeWithValue(E value) {
        return getNodeByValue(value) != null;
    }

    // Получение узла по значению
    private TreeNode<E> getNodeByValue(E value) {
        if (value == null) {
            return null;
        }

        TreeNode<E> currentNode = rootNode;

        while (true) {
            int comparisonResult = comparator.compare(value, currentNode.getValue());

            if (comparisonResult == 0) {
                return currentNode;
            }

            if (comparisonResult < 0) {
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

    private void replaceNodeForReplace(E deletedNodeValue, TreeNode<E> deletedNodeParent, TreeNode<E> nodeForReplace) {
        if (deletedNodeParent == null) {      // В случае удаления корня
            rootNode = nodeForReplace;
        } else if (comparator.compare(deletedNodeValue, deletedNodeParent.getValue()) < 0) {
            deletedNodeParent.setLeft(nodeForReplace);
        } else {
            deletedNodeParent.setRight(nodeForReplace);
        }
    }

    // Удаление первого вхождения узла по значению
    public boolean deleteNode(E value) {
        if (value == null || rootNode == null) {
            return false;
        }

        TreeNode<E> deletedNodeParent = null;
        TreeNode<E> deletedNode = rootNode;

        while (true) {
            int comparisonResult = comparator.compare(value, deletedNode.getValue());

            if (comparisonResult == 0) {
                break;
            }

            deletedNodeParent = deletedNode;

            if (comparisonResult < 0) {
                if (!deletedNode.hasLeft()) {
                    return false;
                }

                deletedNode = deletedNode.getLeft();
            } else {
                if (!deletedNode.hasRight()) {
                    return false;
                }

                deletedNode = deletedNode.getRight();
            }
        }

        // Если у удаляемого узла два ребёнка
        if (deletedNode.hasLeft() && deletedNode.hasRight()) {
            TreeNode<E> nodeForReplaceParent = deletedNode;
            TreeNode<E> nodeForReplace = deletedNode.getRight();

            while (nodeForReplace.hasLeft()) {
                nodeForReplaceParent = nodeForReplace;
                nodeForReplace = nodeForReplace.getLeft();
            }

            int comparisonResult = comparator.compare(nodeForReplace.getValue(), nodeForReplaceParent.getValue());

            nodeForReplace.setLeft(deletedNode.getLeft());

            if (nodeForReplace.hasRight()) {
                nodeForReplaceParent.setLeft(nodeForReplace.getRight());
                nodeForReplace.setRight(deletedNode.getRight());

                replaceNodeForReplace(value, deletedNodeParent, nodeForReplace);
            } else {
                if (comparisonResult < 0) {
                    nodeForReplace.setRight(deletedNode.getRight());

                    replaceNodeForReplace(value, deletedNodeParent, nodeForReplace);

                    nodeForReplaceParent.setLeft(null);
                } else {
                    replaceNodeForReplace(value, deletedNodeParent, nodeForReplace);
                }
            }
            // Если у удаляемого узла нет детей
        } else if (!deletedNode.hasLeft() && !deletedNode.hasRight()) {
            replaceNodeForReplace(value, deletedNodeParent, null);
            // Если у удаляемого узла есть только левый сын
        } else if (deletedNode.hasLeft() && !deletedNode.hasRight()) {
            replaceNodeForReplace(value, deletedNodeParent, deletedNode.getLeft());
            // Если у удаляемого узла есть только правый сын
        } else {
            replaceNodeForReplace(value, deletedNodeParent, deletedNode.getRight());
        }

        --size;

        return true;
    }

    // Обход в глубину с рекурсией
    public void recursiveDepthFirstTraversal(Consumer<E> consumer) {
        if (rootNode == null) {
            return;
        }

        consumer.accept(rootNode.getValue());

        BinarySearchTree<E> leftTree = new BinarySearchTree<>();
        leftTree.rootNode = rootNode.getLeft();
        leftTree.recursiveDepthFirstTraversal(consumer);

        BinarySearchTree<E> rightTree = new BinarySearchTree<>();
        rightTree.rootNode = rootNode.getRight();
        rightTree.recursiveDepthFirstTraversal(consumer);
    }

    // Обход в глубину без рекурсии
    public void depthFirstTraversal(Consumer<E> consumer) {
        Deque<TreeNode<E>> stack = new LinkedList<>();

        stack.offerFirst(rootNode);

        while (!stack.isEmpty()) {
            TreeNode<E> currentNode = stack.pollFirst();
            consumer.accept(currentNode.getValue());

            if (currentNode.hasRight()) {
                stack.offerFirst(currentNode.getRight());
            }

            if (currentNode.hasLeft()) {
                stack.offerFirst(currentNode.getLeft());
            }
        }
    }

    // Обход дерева в ширину
    public void breadthFirstTraversal(Consumer<E> consumer) {
        Queue<TreeNode<E>> queue = new LinkedList<>();

        queue.offer(rootNode);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.poll();

            consumer.accept(currentNode.getValue());

            if (currentNode.hasLeft()) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.hasRight()) {
                queue.offer(currentNode.getRight());
            }
        }
    }
}