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

        Node<E> currentNode = head;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        while (currentNode.hasNext()) {
            stringBuilder.append(currentNode.getData()).append(", ");
            currentNode = currentNode.getNext();
        }

        stringBuilder.append(currentNode.getData());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    // Вспомогательный метод получения узла по индексу.
    private Node<E> getNode(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException(String.format("Попытка метода обратиться к элементу по индексу "
                    + "\"%d\" в пустом списке.", index));
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Попытка метода обратиться к элементу по несуществующему "
                    + "индексу \"%d\", а в списке элементы с индексами от \"0\" до \"%d\".", index, size - 1));
        }

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
        return getNode(index).getData();
    }

    // Изменение значения элемента по указанному индексу (выдает старое значение).
    public E set(int index, E data) {
        Node<E> targetNode = getNode(index);

        E oldData = targetNode.getData();

        targetNode.setData(data);

        return oldData;
    }

    // Удаление элемента по индексу (выдаёт значение элемента).
    public E deleteByIndex(int index) {
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
            throw new IndexOutOfBoundsException(String.format("Попытка вставить в список элемент по несуществующему "
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
        if (size == 0) {                 // Если список пуст.
            return false;
        }

        if (head.getData().equals(data)) {    // Удаление первого элемента, если он содержит искомое значение.
            deleteFirst();
            return true;
        }

        Node<E> currentNode = head;

        while (currentNode.hasNext()) {
            if (currentNode.getNext().getData().equals(data)) {
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

        @SuppressWarnings("unchecked")
        E[] tempArray = (E[]) new Object[size];

        Node<E> currentNode = head;

        for (int i = 0; i < size; ++i) {
            tempArray[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }

        currentNode = head;

        for (int i = size - 1; i >= 0; --i) {
            currentNode.setData(tempArray[i]);
            currentNode = currentNode.getNext();
        }
    }

    // Копирование списка.
    public SinglyLinkedList<E> getCopy() {
        SinglyLinkedList<E> listCopy = new SinglyLinkedList<>();

        if (size == 0) {
            return listCopy;
        }

        Node<E> currentNode = head;
        listCopy.insertFirst(head.getData());
        Node<E> elementCopy = listCopy.head;

        while (currentNode.hasNext()) {
            currentNode = currentNode.getNext();
            elementCopy.setNext(new Node<>(currentNode.getData()));
            elementCopy = elementCopy.getNext();
        }

        listCopy.size = size;

        return listCopy;
    }
}
