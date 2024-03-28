package ru.academits.valyukhov.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private int size;

    // Получение размера списка из переменной size.
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        Node<E> currentNode = head;

        while (currentNode.hasNext()) {
            stringBuilder.append(currentNode.getData()).append(", ");
            currentNode = currentNode.getNext();
        }

        stringBuilder.append(currentNode.getData());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException(String.format("Попытка метода обратиться к элементу по индексу "
                    + "\"%d\" в пустом списке.", index));
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Попытка метода обратиться к элементу по несуществующему "
                    + "индексу \"%d\", а в списке элементы с индексами от \"0\" до \"%d\".", index, size - 1));
        }
    }

    // Вспомогательный метод получения узла по индексу.
    private Node<E> getNode(int index) {
        Node<E> node = head;

        for (int i = 0; i < index; ++i) {
            node = node.getNext();
        }

        return node;
    }

    // Получение значения первого элемента.
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Попытка получить первый элемент из пустого списка.");
        }

        return head.getData();
    }

    // Получение значения элемента по указанному индексу.
    public E get(int index) {
        checkIndex(index);

        return getNode(index).getData();
    }

    // Изменение значения элемента по указанному индексу (выдает старое значение).
    public E set(int index, E data) {
        checkIndex(index);

        Node<E> Node = getNode(index);

        E oldData = Node.getData();

        Node.setData(data);

        return oldData;
    }

    // Удаление элемента по индексу (выдаёт значение элемента).
    public E deleteByIndex(int index) {
        checkIndex(index);

        if (index == 0) {                            // Удаление первого элемента.
            return deleteFirst();
        }

        // Удаление второго, или последующего элемента.
        Node<E> previousNode = getNode(index - 1);

        E deletedData = previousNode.getNext().getData();

        previousNode.setNext(previousNode.getNext().getNext());
        --size;

        return deletedData;
    }

    // Вставка элемента в начало.
    public void insertFirst(E data) {
        head = new Node<>(data, head);
        ++size;
    }

    // Вставка элемента по индексу.
    public void insert(int index, E data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Попытка вставить в список элемент по некорректному "
                    + "индексу \"%d\", а в списке допускается вставка элементов с индексами от \"0\" до \"%d\".", index, size));
        }

        // Вставка на место первого элемента списка.
        if (index == 0) {
            insertFirst(data);
            return;
        }

        // Вставка на место НЕ первого элемента списка.
        Node<E> previousNode = getNode(index - 1);
        previousNode.setNext(new Node<>(data, previousNode.getNext()));
        ++size;
    }

    // Удаление элемента по значению (первое вхождение). Выдаёт true, если элемент был удалён.
    public boolean delete(E data) {
        if (size == 0) {                      // Если список пуст.
            return false;
        }

        if (head.getData() == data || (head.getData() != null && head.getData().equals(data))) {    // Удаление первого элемента, если он содержит искомое значение.
            deleteFirst();
            return true;
        }

        Node<E> currentNode = head;

        while (currentNode.hasNext()) {
            if (currentNode.getNext().getData() == data || (currentNode.getNext().getData() != null && currentNode.getNext().getData().equals(data))) {
                currentNode.setNext(currentNode.getNext().getNext());
                --size;
                return true;
            }

            currentNode = currentNode.getNext();
        }

        return false;
    }

    // Удаление первого элемента. Выдаёт значение элемента.
    public E deleteFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Попытка удалить первый элемент из пустого списка.");
        }

        E deletedData = head.getData();

        head = head.getNext();
        --size;

        return deletedData;
    }

    // Разворот списка за линейное время.
    public void reverse() {
        if (size <= 1) {
            return;
        }

        Node<E> previousNode = null;
        Node<E> currentNode = head;
        Node<E> nextNode = currentNode.getNext();
        currentNode.setNext(previousNode);

        while (nextNode != null) {
            previousNode = currentNode;
            currentNode = nextNode;
            nextNode = nextNode.getNext();
            currentNode.setNext(previousNode);
        }

        head = currentNode;
    }

    // Копирование списка.
    public SinglyLinkedList<E> getCopy() {
        SinglyLinkedList<E> listCopy = new SinglyLinkedList<>();

        if (size == 0) {
            return listCopy;
        }

        listCopy.insertFirst(head.getData());
        Node<E> nodeCopy = listCopy.head;
        Node<E> currentNode = head;

        while (currentNode.hasNext()) {
            currentNode = currentNode.getNext();
            nodeCopy.setNext(new Node<>(currentNode.getData()));
            nodeCopy = nodeCopy.getNext();
        }

        listCopy.size = size;

        return listCopy;
    }
}
