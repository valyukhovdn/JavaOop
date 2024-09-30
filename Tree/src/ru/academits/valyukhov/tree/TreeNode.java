package ru.academits.valyukhov.tree;

public class TreeNode<E> {
    private E value;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode(E value) {
        this.value = value;
    }

    public TreeNode(E value, TreeNode<E> left, TreeNode<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
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

    public void setLeft(TreeNode<E> treeNode) {
        this.left = treeNode;
    }

    public boolean hasRight() {
        return right != null;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> treeNode) {
        this.right = treeNode;
    }
}