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
        if (comparator == null) {
            //noinspection unchecked
            comparator = (Comparator<E>) Comparator.naturalOrder();
        }

        this.comparator = comparator;
    }

    public TreeNode<E> getRootNode() {
        return rootNode;
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

        // Если у удаляемого узла два ребёнка
        if (deletedNode.hasLeft() && deletedNode.hasRight()) {
            TreeNode<E> nodeForReplaceParent = deletedNode;
            TreeNode<E> nodeForReplace = deletedNode.getRight();

            while (nodeForReplace.hasLeft()) {
                nodeForReplaceParent = nodeForReplace;
                nodeForReplace = nodeForReplace.getLeft();
            }

            nodeForReplace.setLeft(deletedNode.getLeft());

            if (nodeForReplaceParent == deletedNode) {
                replaceNode(isDeletedNodeLeftChild, deletedNodeParent, nodeForReplace);
            } else {
                if (nodeForReplace.hasRight()) {
                    nodeForReplaceParent.setLeft(nodeForReplace.getRight());
                } else {
                    nodeForReplaceParent.setLeft(null);
                }

                nodeForReplace.setRight(deletedNode.getRight());
                replaceNode(isDeletedNodeLeftChild, deletedNodeParent, nodeForReplace);
            }
        } else {
            TreeNode<E> deletedNodeChild;

            if (deletedNode.hasLeft()) {                    // Если у удаляемого узла есть только левый сын
                deletedNodeChild = deletedNode.getLeft();
            } else {
                deletedNodeChild = deletedNode.getRight();  // Если у удаляемого узла есть только правый сын, или детей нет
            }

            replaceNode(isDeletedNodeLeftChild, deletedNodeParent, deletedNodeChild);
        }

        --size;

        return true;
    }

    // Обход в глубину с рекурсией
    public void depthFirstTraversalWithRecursion(TreeNode<E> initialNode, Consumer<E> consumer) {
        if (initialNode == null) {
            return;
        }

        consumer.accept(initialNode.getValue());

        if (initialNode.hasLeft()) {
            TreeNode<E> currentLeftNode = initialNode.getLeft();
            this.depthFirstTraversalWithRecursion(currentLeftNode, consumer);
        }

        if (initialNode.hasRight()) {
            TreeNode<E> currentRightNode = initialNode.getRight();
            this.depthFirstTraversalWithRecursion(currentRightNode, consumer);
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
}