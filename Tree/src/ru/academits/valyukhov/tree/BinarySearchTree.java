package ru.academits.valyukhov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    private final Comparator<E> comparator;
    private TreeNode<E> rootNode;
    private int size;

    public BinarySearchTree() {
        //noinspection unchecked
        comparator = (Comparator<E>) Comparator.nullsFirst(Comparator.naturalOrder());
    }

    public BinarySearchTree(Comparator<E> comparator) {
        //noinspection unchecked
        this.comparator = comparator != null ? comparator : (Comparator<E>) Comparator.nullsFirst(Comparator.naturalOrder());
    }

    // Получение числа элементов
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        if (rootNode == null) {
            return "Распечатать это бинарное древо поиска невозможно, т.к. оно пустое.";
        }

        StringBuilder stringBuilder = new StringBuilder();

        String line = "-------------------------------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------------------------------";
        stringBuilder.append(line).append(String.format("%n"));

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
                TreeNode<E> currentNode = outerStack.pop();

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

            stringBuilder.append(String.format("%n"));

            gaps /= 2;

            while (!innerStack.isEmpty()) {
                outerStack.push(innerStack.pop());
            }
        }

        stringBuilder.append(line);

        return stringBuilder.toString();
    }

    // Вставка нового узла по значению
    public void add(E value) {
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
    public boolean containsNodeWithValue(E value) {
        return getNodeByValue(value) != null;
    }

    // Получение узла по значению
    private TreeNode<E> getNodeByValue(E value) {
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

    private void replaceNode(boolean isDeletedNodeLeftChild, TreeNode<E> deletedNodeParent, TreeNode<E> nodeForReplace) {
        if (deletedNodeParent == null) {      // В случае удаления корня
            rootNode = nodeForReplace;
        } else if (isDeletedNodeLeftChild) {
            deletedNodeParent.setLeft(nodeForReplace);
        } else {
            deletedNodeParent.setRight(nodeForReplace);
        }
    }

    // Удаление первого вхождения узла по значению
    public boolean remove(E removedNodeValue) {
        if (rootNode == null) {
            return false;
        }

        TreeNode<E> deletedNodeParent = null;
        TreeNode<E> deletedNode = rootNode;
        boolean isDeletedNodeLeftChild = false;

        while (true) {
            int comparisonResult = comparator.compare(removedNodeValue, deletedNode.getValue());

            if (comparisonResult == 0) {
                break;
            }

            deletedNodeParent = deletedNode;

            if (comparisonResult < 0) {
                if (!deletedNode.hasLeft()) {
                    return false;
                }

                deletedNode = deletedNode.getLeft();
                isDeletedNodeLeftChild = true;
            } else {
                if (!deletedNode.hasRight()) {
                    return false;
                }

                deletedNode = deletedNode.getRight();
                isDeletedNodeLeftChild = false;
            }
        }

        if (!(deletedNode.hasLeft() && deletedNode.hasRight())) {
            replaceNode(isDeletedNodeLeftChild, deletedNodeParent, deletedNode.hasLeft() ? deletedNode.getLeft() : deletedNode.getRight());
        } else { // Если у удаляемого узла два ребёнка
            TreeNode<E> nodeForReplaceParent = deletedNode;
            TreeNode<E> nodeForReplace = deletedNode.getRight();

            while (nodeForReplace.hasLeft()) {
                nodeForReplaceParent = nodeForReplace;
                nodeForReplace = nodeForReplace.getLeft();
            }

            nodeForReplace.setLeft(deletedNode.getLeft());

            if (nodeForReplaceParent != deletedNode) {
                nodeForReplaceParent.setLeft(nodeForReplace.getRight());

                nodeForReplace.setRight(deletedNode.getRight());
            }

            replaceNode(isDeletedNodeLeftChild, deletedNodeParent, nodeForReplace);
        }

        --size;

        return true;
    }

    // Обход дерева в ширину
    public void breadthFirstTraversal(Consumer<E> consumer) {
        if (rootNode == null) {
            return;
        }

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

    // Обход в глубину без рекурсии
    public void depthFirstTraversal(Consumer<E> consumer) {
        if (rootNode == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new LinkedList<>();

        stack.push(rootNode);

        while (!stack.isEmpty()) {
            TreeNode<E> currentNode = stack.pop();
            consumer.accept(currentNode.getValue());

            if (currentNode.hasRight()) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.hasLeft()) {
                stack.push(currentNode.getLeft());
            }
        }
    }

    // Вспомогательный метод обхода в глубину с рекурсией (чтобы public-метод не принимал "node", а начинал обход сразу с корня).
    private void depthFirstTraversalWithRecursion(TreeNode<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node.getValue());

        if (node.hasLeft()) {
            depthFirstTraversalWithRecursion(node.getLeft(), consumer);
        }

        if (node.hasRight()) {
            depthFirstTraversalWithRecursion(node.getRight(), consumer);
        }
    }

    // Обход в глубину с рекурсией
    public void depthFirstTraversalWithRecursion(Consumer<E> consumer) {
        depthFirstTraversalWithRecursion(rootNode, consumer);
    }
}