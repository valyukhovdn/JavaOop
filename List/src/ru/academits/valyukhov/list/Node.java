package ru.academits.valyukhov.list;

public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data == null) {
            throw new NullPointerException("Попытка передать NULL в метод setData.");
        }

        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public boolean hasNext() {
        return getNext() != null;
    }

    @Override
    public String toString() {    // Переопределение метода toString для удобства отладки программы в IntelliJ IDEA.
        return "Node{" +
                "data=" + data +
                '}';
    }
}
