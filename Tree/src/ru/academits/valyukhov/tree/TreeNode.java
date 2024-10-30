package ru.academits.valyukhov.tree;

class TreeNode<E> {
    private E value;
    private TreeNode<E> left;
    private TreeNode<E> right;

    TreeNode(E value) {
        this.value = value;
    }

    E getValue() {
        return value;
    }

    void setValue(E value) {
        this.value = value;
    }

    boolean hasLeft() {
        return left != null;
    }

    TreeNode<E> getLeft() {
        return left;
    }

    void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    boolean hasRight() {
        return right != null;
    }

    TreeNode<E> getRight() {
        return right;
    }

    void setRight(TreeNode<E> right) {
        this.right = right;
    }
}