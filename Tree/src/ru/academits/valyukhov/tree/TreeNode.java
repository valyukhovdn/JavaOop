package ru.academits.valyukhov.tree;

class TreeNode<E> {
    private E value;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    public boolean hasRight() {
        return right != null;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> right) {
        this.right = right;
    }
}